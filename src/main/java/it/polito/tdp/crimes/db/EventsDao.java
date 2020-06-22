package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.Event;
import com.javadocmd.simplelatlng.LatLng;




public class EventsDao {
	
	public List<Event> listAllEvents(int anno, int mese, int giorno){
		String sql = "SELECT * " + 
				"FROM `events` " + 
				"WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? " + 
				"AND DAY(reported_date) = ? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,anno);
			st.setInt(2,mese);
			st.setInt(3,giorno);
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
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
							res.getInt("is_traffic")));
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
	
	
	public List<Integer> listaDistretti(){
		
		String sql = "SELECT DISTINCT district_id " + 
				"FROM `events` " + 
				"ORDER BY district_id" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("district_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
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
	
	public void coordinate(Map<Integer,LatLng> coordinate, int anno){
		
		String sql = "SELECT AVG(geo_lon) AS lon, AVG(geo_lat) AS lat, district_id " + 
				"FROM `events` " + 
				"WHERE YEAR(reported_date) = ? " + 
				"GROUP BY district_id " + 
				"ORDER BY district_id" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					double longitudine = res.getDouble("lon");
					double latitudine = res.getDouble("lat");
					LatLng posizione = new LatLng(latitudine,longitudine);
					coordinate.put(res.getInt("district_id"),posizione);
					//System.out.println(coordinate);
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public List<Integer> listaAnni(){
		
		String sql = "SELECT distinct YEAR(reported_date) AS anno " + 
				"FROM `events` " + 
				"ORDER BY anno" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("anno"));
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
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
	
	
	public List<Integer> listaMesi(int anno){
		
		String sql = "SELECT distinct Month(reported_date) AS mese " + 
				"FROM `events` " + 
				"WHERE YEAR(reported_date) = ? " + 
				"ORDER BY mese" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,anno);
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("mese"));
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
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
	
	
	public List<Integer> listaGiorni(int anno, int mese){
		
		String sql = "SELECT distinct day(reported_date) AS giorno " + 
				"FROM `events` " + 
				"WHERE YEAR(reported_date) = ? AND Month(reported_date) = ? " + 
				"ORDER BY giorno" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,anno);
			st.setInt(2,mese);
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("giorno"));
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
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
	
	
	public int distrettoIniziale(int anno, int mese, int giorno){
		
		String sql = "SELECT district_id, COUNT(*) AS ntot " + 
				"FROM `events` " + 
				"WHERE YEAR(reported_date) = ? AND Month(reported_date) = ? " + 
				"AND day(reported_date) = ? " + 
				"GROUP BY district_id " + 
				"ORDER BY district_id" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,anno);
			st.setInt(2,mese);
			st.setInt(3,giorno);
			int distretto = 0;
			int c = -1;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					int d = res.getInt("district_id");
					int n = res.getInt("ntot");
					
					if(c==-1) {
						distretto = d;
						c = n;
					}else {
						if(n<c) {
							distretto = d;
							c = n;
						}
					}
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return distretto;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

}
	
}
