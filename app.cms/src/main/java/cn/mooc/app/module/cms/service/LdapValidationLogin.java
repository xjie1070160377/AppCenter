package cn.mooc.app.module.cms.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.model.LdapPerson;
import cn.mooc.app.core.service.LdapLoginService;
import cn.mooc.app.core.utils.Md5Encryption;

@Service
public class LdapValidationLogin implements LdapLoginService {
	
	
	@Value("${login.properties.url_addr}")
	private String url_addr;
	@Value("${login.properties.email}")
	String email;
	@Value("${login.properties.password}")
	String password;
	@Value("${login.properties.key}")
	String key;

	@Override
	public LdapPerson login(String username, String pwd) {
		LdapPerson person = new LdapPerson();
		person.setUsername(username);
		person.setPassword(pwd);
		String info =email+"="+username+"&"+password+"="+pwd;
    	URL url;
        BufferedReader bufferedReader = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(url_addr);
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);
                connection.setDoOutput(true);
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(info);
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = reader.readLine())!=null){
                    stringBuilder.append(line);
                }
                String resultStr =stringBuilder.toString().replace("<html>", "").replaceAll("</html>", "").trim();
                //System.out.println("打印Post内容:"+resultStr);
                person.setLoginSuccess(clientCheck(username, resultStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return person;
	}
	
	public  boolean clientCheck(String userAccount,String resultStr){
		String checkStr= Md5Encryption.MD5Encode(userAccount + key);
		if(checkStr.equals(resultStr)){
			return true;
		}
		return false;
	}

}
