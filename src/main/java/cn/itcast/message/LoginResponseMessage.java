package cn.itcast.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LoginResponseMessage extends AbstractResponseMessage {
    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }

    public LoginResponseMessage() {
    }

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }
}
