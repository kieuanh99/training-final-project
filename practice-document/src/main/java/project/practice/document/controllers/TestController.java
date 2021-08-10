package project.practice.document.controllers;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.practice.document.models.*;
import project.practice.document.repository.DocRepository;
import project.practice.document.repository.UserRepository;
import project.practice.document.security.services.DocService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	DocService docService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DocRepository docRepository;
	// USER

	@PostMapping("/addDocument")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addDocument(@RequestBody Document doc) {
		// insert into documents (content) values (<content>)
		return docService.saveDocument(doc);
	}

	// Read
	@GetMapping("/documentUser/{userId}")
	@PreAuthorize("hasRole('USER')")
	public List<Document> findDocsByUserId(@PathVariable Long userId) {
		// select * from documents where id = <id>
		return docService.getDocsByUserId(userId);
	}


	// Read
	@GetMapping("/documentUserByType/{userId}/{type}")
	@PreAuthorize("hasRole('USER')")
	public List<Document> findDocsByType(@PathVariable Long userId, @PathVariable String type) {
		// select * from documents where id = <id>
		return docService.getDocsByType(userId, type);
	}

	// Read
	@GetMapping("/docById/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Document findDocById(@PathVariable Long id) {
		// select * from documents where id = <id>
		return docService.getDocById(id);
	}

	@PutMapping("/updateDocument")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateDocument(@RequestBody Document doc) {
		// update document set content = <content> where id = <id>
		return docService.updateDocument(doc);
	}

	// Delete
	@DeleteMapping("/deleteDocument/{id}")
	@PreAuthorize("hasRole('USER')")
	public String deleteDocument(@PathVariable Long id) {
		// delete from tbl_todo where id = <id>
		return docService.deleteDocument(id);
	}


	@GetMapping("/exportFileById/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void exportFileById(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Document dataDocument = docRepository.findById(id).orElse(null);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+dataDocument.getTitle()+".docx");
		ByteArrayInputStream inputStream = docService.exportFileById(dataDocument);
		IOUtils.copy(inputStream, response.getOutputStream());
	}

	@PostMapping("/exportFile")
	@PreAuthorize("hasRole('USER')")
	public void exportFile(@RequestBody Data data, HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+data.getTitle()+".docx");
		ByteArrayInputStream inputStream = docService.exportFile(data);
		IOUtils.copy(inputStream, response.getOutputStream());
	}

	@PostMapping("/exportFileCA")
	@PreAuthorize("hasRole('USER')")
	public void exportFileCA(@RequestBody DataCA dataCA, HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+dataCA.getTitle()+".docx");
		ByteArrayInputStream inputStream = docService.exportFileCA(dataCA);
		IOUtils.copy(inputStream, response.getOutputStream());
	}

	//ADMIN

	// Get list user by role_user
	@GetMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findAllUsers(@PathVariable int id) {
		// select * from users where id = <id>
		return userRepository.findUserByRoles_Id(id);
	}

	// get all documents
	@GetMapping("/documents")
	public List<Document> findAllDocuments() {
		// select * from documents
		return docService.getDocuments();
	}

	// get list document by type
	@GetMapping("/documentAdminByType/{type}")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Document> findDocumentsByType(@PathVariable String type) {
		// select * from documents where id = <id>
		return docService.getDocumentsByType(type);
	}

	// Update
	@PutMapping("/updateDocStatus")
	@PreAuthorize("hasRole('ADMIN')")
	public Document updateDocStatus(@RequestBody Document doc) {
		// update document set content = <content> where id = <id>
		return docService.updateDocStatus(doc);
	}

}
