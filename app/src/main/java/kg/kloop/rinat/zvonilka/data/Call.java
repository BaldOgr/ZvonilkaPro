package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class Call
{
  private java.util.Date DateCall;
  private java.util.Date updated;
  private String ownerId;
  private String Notes;
  private String objectId;
  private Integer TimeCall;
  private java.util.Date created;
  private BackendlessUser User_ID_Caller;
  private UserData UserData_ID_Client;
  public java.util.Date getDateCall()
  {
    return DateCall;
  }

  public void setDateCall( java.util.Date DateCall )
  {
    this.DateCall = DateCall;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getNotes()
  {
    return Notes;
  }

  public void setNotes( String Notes )
  {
    this.Notes = Notes;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Integer getTimeCall()
  {
    return TimeCall;
  }

  public void setTimeCall( Integer TimeCall )
  {
    this.TimeCall = TimeCall;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public BackendlessUser getUser_ID_Caller()
  {
    return User_ID_Caller;
  }

  public void setUser_ID_Caller( BackendlessUser User_ID_Caller )
  {
    this.User_ID_Caller = User_ID_Caller;
  }

  public UserData getUserData_ID_Client()
  {
    return UserData_ID_Client;
  }

  public void setUserData_ID_Client( UserData UserData_ID_Client )
  {
    this.UserData_ID_Client = UserData_ID_Client;
  }

                                                    
  public Call save()
  {
    return Backendless.Data.of( Call.class ).save( this );
  }

  public Future<Call> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Call> future = new Future<Call>();
      Backendless.Data.of( Call.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Call> callback )
  {
    Backendless.Data.of( Call.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Call.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( Call.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Call.class ).remove( this, callback );
  }

  public static Call findById( String id )
  {
    return Backendless.Data.of( Call.class ).findById( id );
  }

  public static Future<Call> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Call> future = new Future<Call>();
      Backendless.Data.of( Call.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Call> callback )
  {
    Backendless.Data.of( Call.class ).findById( id, callback );
  }

  public static Call findFirst()
  {
    return Backendless.Data.of( Call.class ).findFirst();
  }

  public static Future<Call> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Call> future = new Future<Call>();
      Backendless.Data.of( Call.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Call> callback )
  {
    Backendless.Data.of( Call.class ).findFirst( callback );
  }

  public static Call findLast()
  {
    return Backendless.Data.of( Call.class ).findLast();
  }

  public static Future<Call> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Call> future = new Future<Call>();
      Backendless.Data.of( Call.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Call> callback )
  {
    Backendless.Data.of( Call.class ).findLast( callback );
  }

  public static BackendlessCollection<Call> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Call.class ).find( query );
  }

  public static Future<BackendlessCollection<Call>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Call>> future = new Future<BackendlessCollection<Call>>();
      Backendless.Data.of( Call.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Call>> callback )
  {
    Backendless.Data.of( Call.class ).find( query, callback );
  }
}