package internet.old;

import java.util.List;

import old.position.PositionBean;


public interface positionService {
	public List<PositionBean> sendMessage(String username)throws Exception;
}
