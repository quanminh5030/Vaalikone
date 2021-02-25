package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import persist.Ehdokkaat;

import javax.ws.rs.client.Invocation.Builder;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SAVE_DIR = "images";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		// String savePath =
		// "C:\\Users\\admin\\HelloWord\\vaalikones\\src\\main\\webapp" + File.separator
		// + SAVE_DIR;

		String savePath = "C:/Users/admin/HelloWord/vaalikones/src/main/webapp/" + SAVE_DIR;

//		File fileSaveDir = new File(savePath);

		// get parameters
		Integer candidateId = Integer.parseInt(request.getParameter("candidateId"));
		Part part = request.getPart("file");

		String fileName = extractFileName(part);

		try {
			// part.write(savePath + File.separator + fileName);
//			part.write(savePath + "/" + fileName);
		} catch (Exception e) {
			out.print(e);
		}

		String filePath = SAVE_DIR + "/" + fileName;

		Ehdokkaat candidate = new Ehdokkaat(candidateId);
		candidate.setPicture(filePath);
		try {
			addCandidateImage(candidate);
			out.print("Upload Image Successfully");
			out.print("<br>");
			out.print("<a href='candidateTable.jsp'>Back to Your Information</a>");
		} catch (Exception e) {
			out.print("wrong!" + e);
		}

	}

//	 method for adding image_dir to ehdokkaat table
	private void addCandidateImage(Ehdokkaat candidate) {
		String addURL = "http://localhost:8080/rest/candidateservice/addimage";
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target(addURL);
		Builder b = wt.request();

		Entity<Ehdokkaat> e = Entity.entity(candidate, MediaType.APPLICATION_JSON);

		b.post(e);
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}
