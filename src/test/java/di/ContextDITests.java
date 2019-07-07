package di;

import org.junit.Assert;
import org.junit.Test;

public class ContextDITests {

    @Test
    public void buildServices() {
        Context context = Context.New();
        Assert.assertNotNull(context.accountController());
        Assert.assertNotNull(context.transactionsController());
        Assert.assertNotNull(context.accountService());
        Assert.assertNotNull(context.transactionService());
    }
}
