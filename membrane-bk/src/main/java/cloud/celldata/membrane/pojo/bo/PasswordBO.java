package cloud.celldata.membrane.pojo.bo;

/**
 * 密码实体
 *
 **/
public class PasswordBO {

    // 密码
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 密码实体类构造器
     */
    public static final class PasswordBOBuilder {
        private String password;

        private PasswordBOBuilder() {
        }

        public static PasswordBOBuilder aPasswordBO() {
            return new PasswordBOBuilder();
        }

        public PasswordBOBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public PasswordBO build() {
            PasswordBO passwordBO = new PasswordBO();
            passwordBO.setPassword(password);
            return passwordBO;
        }
    }
}
