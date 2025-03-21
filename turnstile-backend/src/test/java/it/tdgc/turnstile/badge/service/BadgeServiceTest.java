//package it.tdgc.turnstile.badge.service;
//
//
//import it.tdgc.turnstile.dto.BadgeDTO;
//import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
//import it.tdgc.turnstile.model.Badge;
//import it.tdgc.turnstile.repository.BadgeRepository;
//import it.tdgc.turnstile.service.BadgeService;
//import it.tdgc.turnstile.util.ApiResponse;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BadgeServiceTest {
//
//    @Mock
//    BadgeRepository badgeRepository;
//
//    @InjectMocks
//    BadgeService badgeService;  // Let Mockito handle this!
//
//    @Test
//    public void testExample() {
//        assertNotNull(badgeService);
//    }
//
//    @Test
//    public void testGetBadgeById_BadgeFound() {
//        Integer id = 1;
//        Badge badge = new Badge();
//        badge.setId(id);
//        badge.setRfid("foo bar baz");
//
//        when(badgeRepository.findById(id)).thenReturn(Optional.of(badge));
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.getBadgeById(id);
//
//        assertNotNull(result);  // Ensure the ResponseEntity is not null
//        assertEquals(HttpStatus.OK, result.getStatusCode());  // Verify status code is OK
//
//        assertNotNull(result.getBody());
//
//        ApiResponse<BadgeDTO> apiResponse = result.getBody();
//        assertEquals("200", apiResponse.getStatus());  // Assuming your success code is "200"
//        assertEquals("Badge found", apiResponse.getMessage());  // Verify the success message
//
//        BadgeDTO badgeDTO = apiResponse.getData();
//        assertNotNull(badgeDTO);  // Ensure the data is not null
//        assertEquals(id, badgeDTO.getId());  // Check the ID
//        assertEquals("foo bar baz", badgeDTO.getRfid());  // Check the RFID
//    }
//
//
//    @Test
//    public void testGetBadgeById_BadgeNotFound() {
//        Integer id = 99;
//
//        when(badgeRepository.findById(id)).thenReturn(Optional.empty());
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.getBadgeById(id);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());  // Verify status code is 404
//
//        assertNotNull(result.getBody());
//
//        ApiResponse<BadgeDTO> apiResponse = result.getBody();
//        assertEquals("404", apiResponse.getStatus());  // Status should be "404"
//        assertEquals("Badge ID not found", apiResponse.getMessage());  // Check the message
//        assertNull(apiResponse.getData());  // Data should be null because badge was not found
//    }
//
//
//    @Test
//    public void testGetBadgeByRfid_BadgeFound() {
//        String rfid = "foo bar baz";
//        Badge badge = new Badge();
//        badge.setId(1);
//        badge.setRfid(rfid);
//
//        when(badgeRepository.findByRfid(rfid)).thenReturn(Optional.of(badge));
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.getBadgeByRfid(rfid);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//
//        assertNotNull(result.getBody());
//
//        ApiResponse<BadgeDTO> apiResponse = result.getBody();
//        assertEquals("200", apiResponse.getStatus());
//        assertEquals("Badge found", apiResponse.getMessage());
//
//        BadgeDTO badgeDTO = apiResponse.getData();
//        assertNotNull(badgeDTO);
//        assertEquals(1, badgeDTO.getId());
//        assertEquals(rfid, badgeDTO.getRfid());
//    }
//
//    @Test
//    public void testGetBadgeByRfid_BadgeNotFound() {
//        String rfid = "foo bar baz";
//        when(badgeRepository.findByRfid(rfid)).thenReturn(Optional.empty());
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.getBadgeByRfid(rfid);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//
//        assertNotNull(result.getBody());
//    }
//
//    @Test
//    public void testDeleteBadgeById_BadgeFound() {
//        Integer id = 1;
//        Badge badge = new Badge();
//        badge.setId(id);
//        badge.setRfid("foo bar baz");
//
//        when(badgeRepository.findById(id)).thenReturn(Optional.of(badge));
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.deleteBadgeById(id);
//
//        assertNotNull(result);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//
//        assertEquals("200", result.getBody().getStatus());
//        assertEquals("Badge successfully deleted", result.getBody().getMessage());
//        assertNotNull(result.getBody().getData());
//
//        BadgeDTO badgeDTO = result.getBody().getData();
//        assertEquals(id, badgeDTO.getId());
//        assertEquals("foo bar baz", badgeDTO.getRfid());
//        assertNotNull(badgeDTO);
//
//
//        verify(badgeRepository, times(1)).deleteById(id);
//    }
//
//    @Test
//    public void testDeleteBadgeById_BadgeNotFound() {
//        Integer id = 99;
//        Badge badge = new Badge();
//        badge.setId(id);
//        badge.setRfid("foo bar baz");
//
//        when(badgeRepository.findById(id)).thenReturn(Optional.empty());
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.deleteBadgeById(badge.getId());
//
//        assertNotNull(result);
//
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//
//
//        assertEquals("404", result.getBody().getStatus());
//        assertEquals("Badge ID not found", result.getBody().getMessage());
//        assertNull(result.getBody().getData());
//
//
//        verify(badgeRepository, never()).deleteById(id);
//    }
//
//    @Test
//    public void testDeleteBadgeByRfid_BadgeFound() {
//        Integer id = 1;
//        String rfid = "foo bar baz";
//        Badge badge = new Badge();
//        badge.setId(id);
//        badge.setRfid(rfid);
//
//        when(badgeRepository.findByRfid(rfid)).thenReturn(Optional.of(badge));
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.deleteBadgeByRfid(rfid);
//
//        assertNotNull(result);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//
//
//        assertEquals("200", result.getBody().getStatus());
//        assertEquals("Badge successfully deleted", result.getBody().getMessage());
//        assertNotNull(result.getBody().getData());
//
//        BadgeDTO badgeDTO = result.getBody().getData();
//        assertEquals(id, badgeDTO.getId());
//        assertEquals("foo bar baz", badgeDTO.getRfid());
//        assertNotNull(badgeDTO);
//
//        verify(badgeRepository, times(1)).deleteById(id);
//    }
//
//    @Test
//    public void testDeleteBadgeByRfid_BadgeNotFound() {
//        Integer id = 99;
//        String rfid = "foo bar baz";
//        Badge badge = new Badge();
//        badge.setId(id);
//        badge.setRfid(rfid);
//
//        when(badgeRepository.findByRfid(rfid)).thenReturn(Optional.empty());
//
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.deleteBadgeByRfid(rfid);
//
//        assertNotNull(result);
//
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//
//
//        assertEquals("404", result.getBody().getStatus());
//        assertEquals("Badge RFID not found", result.getBody().getMessage());
//        assertNull(result.getBody().getData());
//    }
//
//    @Test
//    public void testUpdateBadge_BadgeFound() {
//        Integer id = 1;
//        Badge badgeToUpdate = new Badge();
//        badgeToUpdate.setId(id);
//        badgeToUpdate.setRfid("oldRfid");
//
//        Badge newBadgeData = new Badge();
//        newBadgeData.setId(id);
//        newBadgeData.setRfid("newRfid");
//
//        // Mocking the repository behavior
//        when(badgeRepository.findById(id)).thenReturn(Optional.of(badgeToUpdate));
//        when(badgeRepository.save(any(Badge.class))).thenReturn(newBadgeData);
//
//        // Calling the service method
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.updateBadge(newBadgeData);
//
//        // Verifying the result
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertNotNull(result.getBody());
//        assertEquals("204", result.getBody().getStatus());
//        assertEquals("Badge updated successfully", result.getBody().getMessage());
//
//        // Verifying that the save method was called
//        verify(badgeRepository, times(1)).save(any(Badge.class));
//    }
//
//    // Test for updating a badge when it is not found
//    @Test
//    public void testUpdateBadge_BadgeNotFound() {
//        Integer id = 1;
//        Badge newBadgeData = new Badge();
//        newBadgeData.setId(id);
//        newBadgeData.setRfid("newRfid");
//
//        // Mocking the repository behavior
//        when(badgeRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Calling the service method
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.updateBadge(newBadgeData);
//
//        // Verifying the result
//        assertNotNull(result);
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//        assertNotNull(result.getBody());
//        assertEquals("404", result.getBody().getStatus());
//        assertEquals("Badge ID not found", result.getBody().getMessage());
//
//        // Verifying that save method was not called
//        verify(badgeRepository, times(0)).save(any(Badge.class));
//    }
//
//    // Test for inserting a new badge
//    @Test
//    public void testInsertBadge() throws BadgeAlreadyExistsException {
//        Integer id = 1;
//        Badge badgeToInsert = new Badge();
//        badgeToInsert.setId(id);
//        badgeToInsert.setRfid("newRfid");
//
//        // Mocking the repository behavior
//        when(badgeRepository.save(any(Badge.class))).thenReturn(badgeToInsert);
//
//        // Calling the service method
//        ResponseEntity<ApiResponse<BadgeDTO>> result = badgeService.insertBadge(badgeToInsert);
//
//        // Verifying the result
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertNotNull(result.getBody());
//        assertEquals("200", result.getBody().getStatus());
//        assertEquals("Badge created successfully", result.getBody().getMessage());
//        assertEquals("newRfid", result.getBody().getData().getRfid());
//
//        // Verifying that save method was called
//        verify(badgeRepository, times(1)).save(any(Badge.class));
//    }
//
//}
