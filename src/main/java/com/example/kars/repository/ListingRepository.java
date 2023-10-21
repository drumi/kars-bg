package com.example.kars.repository;

import com.example.kars.dto.post.ListingServiceDto;
import com.example.kars.repository.exception.AccessException;
import com.example.kars.repository.exception.CreationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListingRepository {

    @Autowired
    private DataSource dataSource;

    public List<ListingServiceDto> getAll() {
        return extractAllListings("SELECT username, title, price, description, picture FROM listing");
    }

    public List<ListingServiceDto> getAllOrderedByTime() {
        return extractAllListings("SELECT * FROM listing ORDER BY created_at DESC");
    }

    private ListingServiceDto createListingFromResultSet(ResultSet rs) throws SQLException {
        var listing = new ListingServiceDto();

        listing.setUsername(rs.getString("username"));
        listing.setTitle(rs.getString("title"));
        listing.setPrice(rs.getInt("price"));
        listing.setDescription(rs.getString("description"));
        try {
            listing.setPicture(
                ImageIO.read(
                    new ByteArrayInputStream(rs.getBytes("picture"))
                )
            );
        } catch (IOException e) {
            throw new AccessException("Could not decode image", e);
        }

        return listing;
    }

    private List<ListingServiceDto> extractAllListings(String sql) {
        List<ListingServiceDto> listings = new ArrayList<>();

        try (var conn = dataSource.getConnection()){
            var stmt = conn.prepareStatement(sql);

            var rs = stmt.executeQuery();

            while (rs.next()) {
                listings.add(
                    createListingFromResultSet(rs)
                );
            }
        } catch (SQLException e) {
            throw new AccessException("Could not load the data", e);
        }

        return listings;
    }



    public void create(ListingServiceDto listing) {
        byte[] pictureBytes;
        try {
            pictureBytes = convertToJpgBytes(listing.getPicture());
        } catch (IOException e) {
            throw new CreationException("Could not convert image bytes", e);
        }

        try (var conn = dataSource.getConnection()){
            var stmt = conn.prepareStatement(
                "INSERT INTO listing (username, title, price, description, picture) VALUES (?, ?, ?, ?, ?);"
            );

            stmt.setString(1, listing.getUsername());
            stmt.setString(2, listing.getTitle());
            stmt.setInt(3, listing.getPrice());
            stmt.setString(4, listing.getDescription());
            stmt.setBytes(5, pictureBytes);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new CreationException("Could not save listing into the database", e);
        }
    }

    private byte[] convertToJpgBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos );
        return baos.toByteArray();
    }
}
