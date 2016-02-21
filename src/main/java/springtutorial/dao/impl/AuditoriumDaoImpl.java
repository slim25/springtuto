package springtutorial.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springtutorial.dao.AuditoriumDao;
import springtutorial.exception.NotExistSuchAuditorium;
import springtutorial.model.Auditorium;

@Repository
public class AuditoriumDaoImpl implements AuditoriumDao {
	private final String SQL_INSERT_AUDITORIUM = "INSERT INTO auditorium (name, numberOfSeats) VALUES (?, ?)";
	private final String SQL_GET_AUDITORIUM_ID_BY_NAME = "SELECT id FROM auditorium WHERE name=?";
	private final String SQL_SET_AUDITORIUM_VIP_SEAT = "INSERT INTO vip_seats (auditorium_id, vip_seat_number)"
			+ " VALUES (?, ?)";
	private final String SQL_GET_AUDITORIUM_VIP_SEAT = "SELECT vip_seat_number FROM vip_seats WHERE auditorium_id=?";
	private final String SQL_GET_SEATS_NUMBER = "SELECT numberOfSeats FROM auditorium WHERE id=?";
	private final String SQL_GET_AUDITORIUMS = "SELECT * FROM auditorium";
	
	public static List<Auditorium> auditoriums;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public AuditoriumDaoImpl(List<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}
	
	public AuditoriumDaoImpl() {
	}

	public Integer getSeatsNumber(Integer auditoriumId)
			throws NotExistSuchAuditorium {
		Integer numberOfSeats = (Integer)jdbcTemplate.queryForObject(SQL_GET_SEATS_NUMBER, new Object[] { auditoriumId }, Integer.class);
		if(numberOfSeats != null && numberOfSeats != 0 ) return numberOfSeats;
		throw new NotExistSuchAuditorium();
	}

	public List<Integer> getVipSeats(Integer auditoriumId){
		List<Integer> vipSeats = new ArrayList<>();
		
		List<Map<String, Object>> vipSeatsRows = jdbcTemplate.queryForList(SQL_GET_AUDITORIUM_VIP_SEAT,auditoriumId);
		for (Map<?, ?> vipSeatRow : vipSeatsRows) {
			vipSeats.add((Integer) vipSeatRow.get("vip_seat_number"));
		}
		return vipSeats;
	}

	public List<Auditorium> getAuditoriums() {
		List<Auditorium> auditoriums = new ArrayList<>();
		
		
		List<Map<String, Object>> auditoriumRows = jdbcTemplate.queryForList(SQL_GET_AUDITORIUMS);
		for (Map<?, ?> row : auditoriumRows) {
			Auditorium auditorium = new Auditorium();
			auditorium.setId((Integer)row.get("id"));
			auditorium.setName((String)row.get("name"));
			auditorium.setNumberOfSeats((Integer)row.get("numberOfSeats"));
			auditorium.getVipSeats().addAll(getVipSeats(auditorium.getId()));
			auditoriums.add(auditorium);
		}
		return auditoriums;
	}
	
	public void initDefaultDBData(){
		
			jdbcTemplate.batchUpdate(SQL_INSERT_AUDITORIUM, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Auditorium auditorium = auditoriums.get(i);
					ps.setString(1, auditorium.getName());
					ps.setInt(2, auditorium.getNumberOfSeats());
				}
						
				@Override
				public int getBatchSize() {
					return auditoriums.size();
				}
			  });
			for(Auditorium auditorium : auditoriums){
				List<Integer> vipSeats = auditorium.getVipSeats();
				if(vipSeats != null && !vipSeats.isEmpty()){
					Integer auditoriumId = (Integer)jdbcTemplate.queryForObject(
							SQL_GET_AUDITORIUM_ID_BY_NAME, new Object[] { auditorium.getName() }, Integer.class);
					initAuditoriumVipSeats(auditoriumId, auditorium.getVipSeats());
				}
			}
			
	}
	
	private void initAuditoriumVipSeats(final Integer auditoriumId, final List<Integer> vipSeats){
		
			jdbcTemplate.batchUpdate(SQL_SET_AUDITORIUM_VIP_SEAT, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Integer vipSeat = vipSeats.get(i);
					ps.setInt(1, auditoriumId);
					ps.setInt(2, vipSeat);
				}
						
				@Override
				public int getBatchSize() {
					return vipSeats.size();
				}
			  });
			
		
	}
	

}
