package autotests.system;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Monitoring implements ITestListener {


    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Start test " + iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Success test " + iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println(iTestResult.getMethod().getMethodName() + " FAILURE");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println(iTestResult.getMethod().getMethodName() + "SKIP");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but within success percentage: " + iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("----------START ALL------------\\n\\n");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("----------FINISH ALL-----------\\n\\n");
    }
}
