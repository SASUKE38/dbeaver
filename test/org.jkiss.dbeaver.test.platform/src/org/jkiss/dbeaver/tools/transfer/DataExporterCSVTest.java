import org.jkiss.dbeaver.model.data.DBDAttributeBinding;
import org.jkiss.dbeaver.model.exec.DBCSession;
import org.jkiss.dbeaver.model.exec.DBCResultSet;
import org.jkiss.dbeaver.tools.transfer.stream.IStreamDataExporterSite;
import org.jkiss.dbeaver.tools.transfer.stream.exporter.DataExporterCSV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

public class DataExporterCSVTest {

    private DataExporterCSV dataExporterCSV;
    private StringWriter stringWriter;
    private IStreamDataExporterSite site;

    @Before
    public void setUp() {
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        site = Mockito.mock(IStreamDataExporterSite.class);
        Mockito.when(site.getWriter()).thenReturn(printWriter);

        dataExporterCSV = new DataExporterCSV();
        try {
            dataExporterCSV.init(site);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportHeader() {
        // Mocking attributes
        DBDAttributeBinding[] columns = new DBDAttributeBinding[3];
        columns[0] = Mockito.mock(DBDAttributeBinding.class);
        Mockito.when(columns[0].getName()).thenReturn("ID");
        Mockito.when(columns[0].getLabel()).thenReturn("Identifier");

        columns[1] = Mockito.mock(DBDAttributeBinding.class);
        Mockito.when(columns[1].getName()).thenReturn("NAME");
        Mockito.when(columns[1].getLabel()).thenReturn("Name");

        columns[2] = Mockito.mock(DBDAttributeBinding.class);
        Mockito.when(columns[2].getName()).thenReturn("AGE");
        Mockito.when(columns[2].getLabel()).thenReturn("Age");

        Mockito.when(site.getAttributes()).thenReturn(columns);
        Mockito.when(site.getProperties()).thenReturn(new HashMap<>());

        try {
            dataExporterCSV.exportHeader(Mockito.mock(DBCSession.class));

            String expectedHeader = "\"IDENTIFIER\",\"NAME\",\"AGE\",";
            Assert.assertEquals(expectedHeader, stringWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }
}
