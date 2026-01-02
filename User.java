package OnlineStoreApp;

import java.io.Serializable;

class User implements Serializable {
	String username, password;
	Role role;

	User(String u, String p, Role r) {
		username = u;
		password = p;
		role = r;
	}
}