package cn.csmooc.jcb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AppCenterCodeBuilder {

	public static void main(String[] args) throws IOException {
		String directory = "E:/java/moocCenter/AppCenter/app.degree/src/main/java/cn/mooc/app/module/degree/";
		String jspDirectory = "E:/java/moocCenter/AppCenter/app.degree/src/main/resources/views/default/degree/";
		

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("packageName", "cn.mooc.app.module.degree");
		root.put("moduleController", "DegreeController");
		root.put("module", "degree");
		root.put("author", "linwei");
		root.put("baseName", "Xsrz");
		root.put("entity", "xsrz");
		root.put("pkType", "Integer");
		root.put("manager", "学术任职");
		root.put("newDate", new Date());

		root.put("entityName", "${entity.name}");
		root.put("entityId", "${entity.id}");	
		root.put("action", root.get("entity") + "${entity.id == null ? 'Save' : 'Update'}");
		Configuration cfg = new Configuration();
		String templatePath = AppCenterCodeBuilder.class.getResource("").getPath() + "/template";
		cfg.setDirectoryForTemplateLoading(new File(templatePath));

		controllerCodeBuilder(directory, root, cfg);
		serviceCodeBuilder(directory, root, cfg);
		daoCodeBuilder(directory, root, cfg);
		listJspBuilder(jspDirectory, root, cfg);
		formJspBuilder(jspDirectory, root, cfg);
		System.out.println(root.get("baseName") + "-----------------------success!");
	}

	public static void controllerCodeBuilder(String directory, Map<String, Object> root, Configuration cfg) throws IOException {
		try {
			Template template = cfg.getTemplate("/controller.ftl");
			File output = new File(directory + "mcenter/controller" + File.separator + root.get("baseName") + "Controller.java");
			if (output.exists()) {
				output.delete();
			}
			Writer writer = new FileWriter(output);
			template.process(root, writer);
			System.out.println("action success!");
		} catch (IOException e) {
			System.out.println("Cause==>" + e.getCause());
		} catch (TemplateException e) {
			System.out.println("Cause==>" + e.getCause());
		}
	}

	public static void serviceCodeBuilder(String directory, Map<String, Object> root, Configuration cfg) throws IOException {
		try {
			Template template = cfg.getTemplate("/service.ftl");
			File output = new File(directory + "service" + File.separator + root.get("baseName") + "Service.java");
			if (output.exists()) {
				output.delete();
			}
			Writer writer = new FileWriter(output);
			template.process(root, writer);
			System.out.println("service success!");
		} catch (IOException e) {
			System.out.println("Cause==>" + e.getCause());
		} catch (TemplateException e) {
			System.out.println("Cause==>" + e.getCause());
		}
	}

	public static void daoCodeBuilder(String directory, Map<String, Object> root, Configuration cfg) throws IOException {
		try {
			Template template = cfg.getTemplate("/dao.ftl");
			File output = new File(directory + "data/rds" + File.separator + root.get("baseName") + "Repository.java");
			if (output.exists()) {
				output.delete();
			}
			Writer writer = new FileWriter(output);
			template.process(root, writer);
			System.out.println("dao success!");
		} catch (IOException e) {
			System.out.println("Cause==>" + e.getCause());
		} catch (TemplateException e) {
			System.out.println("Cause==>" + e.getCause());
		}
	}

	public static void listJspBuilder(String directory, Map<String, Object> root, Configuration cfg) throws IOException {
		try {
			Template template = cfg.getTemplate("/list.ftl");
			File directoryFile = new File(directory + root.get("entity"));
			if (!directoryFile.exists()) {
				directoryFile.mkdir();
			}
			File output = new File(directory + root.get("entity") + File.separator + root.get("entity") + "List.html");
			if (output.exists()) {
				output.delete();
			}
			Writer writer = new FileWriter(output);
			template.process(root, writer);
			System.out.println("list success!");
		} catch (IOException e) {
			System.out.println("Cause==>" + e.getCause());
		} catch (TemplateException e) {
			System.out.println("Cause==>" + e.getCause());
		}
	}

	public static void formJspBuilder(String directory, Map<String, Object> root, Configuration cfg) throws IOException {
		try {
			Template template = cfg.getTemplate("/form.ftl");
			File output = new File(directory + root.get("entity") + File.separator + root.get("entity") + "Form.html");
			if (output.exists()) {
				output.delete();
			}
			Writer writer = new FileWriter(output);
			template.process(root, writer);
			System.out.println("form success!");
		} catch (IOException e) {
			System.out.println("Cause==>" + e.getCause());
		} catch (TemplateException e) {
			System.out.println("Cause==>" + e.getCause());
		}
	}

}
