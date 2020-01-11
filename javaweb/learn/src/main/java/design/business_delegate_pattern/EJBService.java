package design.business_delegate_pattern;

/**
 * 实体服务类
 */
public class EJBService implements BusinessService {

    @Override
    public void doProcessing() {
        System.out.println("Processing task by invoking EJB Service");
    }

}
