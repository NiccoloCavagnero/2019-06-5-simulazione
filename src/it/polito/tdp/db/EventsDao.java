package it.polito.tdp.db;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(Year year, String month, String day, Map<Integer,District> dMap){
		String sql = "SELECT * FROM events WHERE date(reported_date)=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, year.getValue()+"-"+month+"-"+day);
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Event e = new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic"));
					list.add(e);
					dMap.get(e.getDistrict_id()).getList().add(e);
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public District safer(Year year, Map<Integer,District> dMap){
		String sql = "SELECT district_id AS id, COUNT(*) AS c FROM events WHERE \n"+
	                 "year(reported_date)=? GROUP BY id ORDER BY c ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,year.getValue());
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			District d = null;
			if (res.next()!=false) {
				try {
					d = dMap.get(res.getInt("id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return d ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Year> yearsList(){
		String sql = "SELECT DISTINCT year(reported_date) AS year\n " + 
				     "FROM events\n " + 
				     "ORDER BY year(reported_date) ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Year> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(Year.of(res.getInt("year")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<District> districtsList(Year year, Map<Integer,District> dMap){
		String sql = "SELECT DISTINCT district_id AS id, AVG(geo_lon) AS avgLong, AVG(geo_lat) AS avgLat\n " + 
				     "FROM events\n " + 
				     "WHERE year(reported_date)=?\n "+
				     "GROUP BY district_id" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year.getValue());
			
			List<District> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					District d = new District(res.getInt("id"),res.getDouble("avgLong"),res.getDouble("avgLat"));
					list.add(d);
					dMap.put(d.getId(),d);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
