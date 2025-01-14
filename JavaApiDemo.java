import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface IMessage {
    void send(String msg);
}

class MessageImpl implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("消息发送" + msg);
    }
}

class NetMessageImpl implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("[网络消息发送]" + msg);
    }
}

class Factory {
    private Factory() {}

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        try {
            return (T) new MessageProxy().bind(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class MessageProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public boolean connect() {
        System.out.println("[代理操作]进行消息发送通道的连接。");
        return true;
    }

    public void close() {
        System.out.println("[代理操作]关闭连接通道。");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (this.connect()) {
                return method.invoke(this.target, args);
            } else {
                throw new Exception("[ERROR]消息无法进行发送!");
            }
        } finally {
            this.close();
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface UseMessage {
    Class<? extends IMessage> clazz();
}

@UseMessage(clazz = MessageImpl.class) // 默认使用 MessageImpl
class MessageService {
    private IMessage message;

    public MessageService() {
        UseMessage use = MessageService.class.getAnnotation(UseMessage.class);
        if (use != null) {
            this.message = Factory.getInstance(use.clazz());
        } else {
            // 没有注解时的默认行为，可以抛出异常或使用默认实现
            this.message = Factory.getInstance(MessageImpl.class); // 提供默认实现
            System.out.println("No @UseMessage annotation found. Using default MessageImpl.");
        }
    }

    public void send(String msg) {
        this.message.send(msg);
    }
}

public class JavaApiDemo {
    public static void main(String[] args) throws Exception {
        MessageService messageService = new MessageService();
        messageService.send("www.mldn.cn");

        // 测试更换实现类
        @UseMessage(clazz = NetMessageImpl.class)
        class AnotherMessageService extends MessageService {} // 临时创建子类并注解

        AnotherMessageService anotherService = new AnotherMessageService();
        anotherService.send("www.google.com");
    }
}