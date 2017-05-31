package bankproject.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import bankproject.entities.AbstractEntity;
import bankproject.exceptions.SrvException;

public class SrvOperation extends AbstractService {


	@Override
	public void save(AbstractEntity entity) throws SrvException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AbstractEntity populateEntity(ResultSet rs) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
