package com.maite.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Clase que da inicio el contexto de spring 
 *  
 */
@SpringBootApplication
@ComponentScan("com")
public class DocumentExtractorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentExtractorApplication.class, args);
	}

/*	@Override
	public void run(String... arg0) throws Exception {
		 
			List<File> filesInFolder = Files.walk(Paths.get(com.maite.properties.getPathIn())).filter(p -> p.toString().endsWith(".pdf"))
					.map(Path::toFile).collect(Collectors.toList());
			
			Iterator<File> it = filesInFolder.iterator();

			if (it.hasNext()) {
				File file = it.next();
				it.remove();
				InputStream document = new FileInputStream(file);
				String ocr = documentConverter.convert(document);
				BufferedWriter out = new BufferedWriter(new FileWriter(new File(com.maite.properties.getPathOut() + "\\" + file.getName().replace(".pdf", ".txt"))));
				out.write(ocr);
				out.close();
		}
			
	}
*/

}
