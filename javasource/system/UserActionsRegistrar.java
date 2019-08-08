package system;

import com.mendix.core.actionmanagement.IActionRegistrator;

public class UserActionsRegistrar
{
  public void registerActions(IActionRegistrator registrator)
  {
    registrator.bundleComponentLoaded();
    registrator.registerUserAction(mindspheresinglesignon.actions.CalcBase64Credentials.class);
    registrator.registerUserAction(mindspheresinglesignon.actions.Get_MDSP_ENV.class);
    registrator.registerUserAction(mindspheresinglesignon.actions.GetAccessToken.class);
    registrator.registerUserAction(mindspheresinglesignon.actions.MindSphereAccessTokenConnector.class);
    registrator.registerUserAction(mindspheresinglesignon.actions.PutAccessToken.class);
    registrator.registerUserAction(mindspheresinglesignon.actions.RunningLocal.class);
    registrator.registerUserAction(mindspheresinglesignon.actions.SingleSignOn.class);
    registrator.registerUserAction(system.actions.VerifyPassword.class);
  }
}
