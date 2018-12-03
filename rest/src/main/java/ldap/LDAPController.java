package ldap;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/ldap")
public class LDAPController {


	@GetMapping(path = "/checkUser/{uid}/{pwhash}")
	public boolean checkUser(@PathVariable String uid, @PathVariable String pwhash) throws LdapException {
		return LDAPRepository.checkUser(uid, pwhash);
	}
	
	
}
