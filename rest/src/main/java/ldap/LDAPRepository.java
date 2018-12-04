package ldap;

import java.io.IOException;

import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Value;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

public class LDAPRepository {
	static LdapConnection connection;
	
	private static void connect() {
		connection = new LdapNetworkConnection( "localhost", 10389 );
	}
	
	private static void disconnect() {
		try {
			if(connection.isConnected()) {
				connection.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static boolean checkUser(String id, String pw) throws LdapException {
		connect();
		Entry user = connection.lookup("cn=" + id + ",ou=users,dc=fh-aachen,dc=de");
		connection.loadSchema();
		Attribute pwAtt = user.get("userPassword");
		Value pwValue = pwAtt.get();
		String pwLDAP = pwValue.getValue();
		boolean result = pwLDAP.equals(pw);
		disconnect();
		return result;
	}
}
