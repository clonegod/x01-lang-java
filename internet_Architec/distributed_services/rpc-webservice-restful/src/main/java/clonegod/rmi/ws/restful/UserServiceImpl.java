package clonegod.rmi.ws.restful;

import static clonegod.rmi.ws.restful.Storage.USERS;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

	@Override
	public Response getAll() {
		Response res = Response.success();
		res.setData(new ArrayList<>(USERS.values()));
		return res;
	}

	@Override
	public Response get(int id) {
		Response res = Response.success();
		res.setData(USERS.get(id));
		return res;
	}

	@Override
	public Response insert(User user) {
		return update(user);
	}

	@Override
	public Response delete(int id) {
		USERS.remove(id);
		return Response.success();
	}

	@Override
	public Response update(User user) {
		USERS.put(user.getId(), user);
		return Response.success();
	}

}
