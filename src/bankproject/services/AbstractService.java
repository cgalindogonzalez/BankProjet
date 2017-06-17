package bankproject.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import bankproject.entities.AbstractEntity;
import bankproject.exceptions.SrvException;

public abstract class AbstractService {
	private String entitySqlTable;
	private DatabaseManager dbManager;


	/**
	 * getter
	 * @return entitySqlTable
	 */
	public String getEntitySqlTable() {
		return entitySqlTable;
	}

	/**
	 * setter
	 * @param entitySqlTable
	 */
	public void setEntitySqlTable(String entitySqlTable) {
		this.entitySqlTable = entitySqlTable;
	}
	
	/**
	 * getter
	 * @return
	 */
	public DatabaseManager getDbManager() {
		return this.dbManager;
	}

	/**
	 * setter
	 * @param dbManager
	 */
	public void setDbManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}
	
	
	/**
	 * Create or update entity
	 * 
	 * @param entity
	 * @throws SrvException 
	 * @throws SQLException 
	 */
	public abstract void save(AbstractEntity entity) throws SrvException, SQLException;
	
	/**
	 * Get Entity by Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AbstractEntity get(String col, Integer id) throws Exception {
		Connection connexion = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		AbstractEntity result = null;
		
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(getEntitySqlTable());
		query.append(" WHERE ");
		query.append(col);
		query.append(" = ?");
		System.out.println(query.toString());
		try {
			DatabaseManager dbm = this.getDbManager();
			connexion = dbm.getConnection();
			
			pst = connexion.prepareStatement(query.toString());
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				result = populateEntity(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pst != null) {
				pst.close();
			}
			if (connexion != null) {
				connexion.close();
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 * @throws Exception 
	 */
	protected abstract AbstractEntity populateEntity(ResultSet rs) throws SQLException, Exception;
	
	/**
	 * Get collection of entities by ids
	 * 
	 * @param ids
	 * @return
	 */
	public Collection<AbstractEntity> get(Collection<Integer> ids, String idEntity)  throws Exception {
		Connection connexion = null;
		Statement st = null;
		ResultSet rs = null;
		Collection<AbstractEntity> results = new LinkedHashSet<AbstractEntity>();
		
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(getEntitySqlTable());
		query.append(" WHERE ");
		query.append(idEntity);
		query.append(" IN (");
		
		Iterator<Integer> it = ids.iterator();
		do {
			query.append(it.next());
			if (it.hasNext()) {
				query.append(",");
			}
		} while(it.hasNext());
		
		query.append(")");
		query.append(" ORDER BY ");
		query.append(idEntity);
		System.out.println(query.toString());
		
		try {
			connexion = getDbManager().getConnection();
			st = connexion.createStatement();
			rs = st.executeQuery(query.toString());
			
			while (rs.next()) {
				results.add(populateEntity(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (st != null) {
				st.close();
			}
			if (connexion != null) {
				connexion.close();
			}
			
			
		}
		
		return results;
	}
	
	public Collection<AbstractEntity> getAll()  throws Exception {
		Connection connexion = null;
		Statement st = null;
		ResultSet rs = null;
		Collection<AbstractEntity> results = new LinkedHashSet<AbstractEntity>();
		
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(getEntitySqlTable());
		
		
		
		try {
			connexion = getDbManager().getConnection();
			st = connexion.createStatement();
			rs = st.executeQuery(query.toString());
			
			while (rs.next()) {
				results.add(populateEntity(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (st != null) {
				st.close();
			}
			if (connexion != null) {
				connexion.close();
			}
			
			
		}
		
		return results;
	}
	
	public static void main(String[] args) {
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append("customer");
		query.append(" WHERE id = ?");
		System.out.println(query.toString());
		
	}
}

