package hr.fer.zemris.java.tecaj_13;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class Inicijalizacija implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        String path = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
        Map<String, String> propertiesMap = null;
        ComboPooledDataSource cpds = new ComboPooledDataSource();


        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
            propertiesMap = readPropertiesMap(Paths.get(path));
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
        } catch (IOException e) {
            //TODO handlaj gresku koja nastane ako je greska pri citanju postavki za datapool
            System.exit(0);
        }

        String host = propertiesMap.get("host");
        String port = propertiesMap.get("port");
        String name = propertiesMap.get("name");
        String user = propertiesMap.get("user");
        String password = propertiesMap.get("password");

        if(host == null || port == null || name == null || user == null || password == null){
            //TODO handle error
            System.exit(-1);
        }

        String connectionURL =
                "jdbc:derby://"+host
                +":"+port
                +"/"+name
                +";user="+user
                +";password="+password;

        cpds.setJdbcUrl(connectionURL);

        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private Map<String, String> readPropertiesMap(Path path) throws IOException {

        Map<String, String> propertiesMap = new HashMap<>();

        List<String> lines;
        lines = Files.readAllLines(path);

        for (String line : lines) {
            System.out.println(line);
            String[] split = line.split("\\=");
            propertiesMap.put(split[0], split[1]);
        }

        return propertiesMap;
    }

}
