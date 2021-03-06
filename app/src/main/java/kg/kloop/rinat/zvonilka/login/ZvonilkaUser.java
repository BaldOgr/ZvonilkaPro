package kg.kloop.rinat.zvonilka.login;

import com.backendless.BackendlessUser;

public class ZvonilkaUser extends BackendlessUser
{
  public String getEmail()
  {
    return super.getEmail();
  }

  public void setEmail( String email )
  {
    super.setEmail( email );
  }

  public String getPassword()
  {
    return super.getPassword();
  }

  public String getName()
  {
    return (String) super.getProperty( "NAME" );
  }

  public void setName( String name )
  {
    super.setProperty( "NAME", name );
  }
}