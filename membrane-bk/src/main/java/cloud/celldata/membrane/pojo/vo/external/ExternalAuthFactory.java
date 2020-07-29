package cloud.celldata.membrane.pojo.vo.external;

/**
 * 外部认证对象工厂方法接口（函数式接口）
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.pojo.vo.external
 * @ClassName: ExternalAuthFactory
 * @Description: 外部认证对象工厂方法接口（函数式接口）
 * @Author: jiwang
 * @CreateDate: 2020/6/22 15:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 15:40
 */
public interface ExternalAuthFactory<A extends ExternalAuth> {
    A creat(ExternalAuthInfo externalAuthInfo);
}
