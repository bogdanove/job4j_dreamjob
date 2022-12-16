package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {

    private final static String FIND_ALL = "SELECT * FROM candidate";
    private final static String ADD = "INSERT INTO candidate(name, description, created, city_id, photo) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)";
    private final static String UPDATE = "UPDATE candidate set name = ?, description = ?, city_id = ?, photo = ? where id = ?";
    private final static String FIND_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private final static String CLEAN = "TRUNCATE TABLE candidate RESTART IDENTITY";

    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    private static final Logger LOG = LoggerFactory.getLogger(CandidateDbStore.class.getName());

    public List<Candidate> findAll() {
        List<Candidate> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(candidateFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }


    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(3, candidate.getCity().getId());
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(3, candidate.getCity().getId());
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return candidateFactory(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    private Candidate candidateFactory(ResultSet it) throws SQLException {
        return new Candidate(it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getDate("created").toLocalDate(),
                new City(it.getInt("city_id")),
                it.getBytes("photo"));
    }

    public void clean() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(CLEAN)
        ) {
            LOG.info("Table CANDIDATE successfully clear");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

