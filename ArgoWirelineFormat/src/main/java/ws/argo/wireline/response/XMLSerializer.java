package ws.argo.wireline.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import ws.argo.wireline.probe.ProbeParseException;
import ws.argo.wireline.probe.ProbeWrapper;
import ws.argo.wireline.probe.xml.Probe;
import ws.argo.wireline.response.ServiceWrapper.AccessPoint;
import ws.argo.wireline.response.xml.ObjectFactory;
import ws.argo.wireline.response.xml.Services;
import ws.argo.wireline.response.xml.Services.Service;
import ws.argo.wireline.response.xml.Services.Service.AccessPoints;
import ws.argo.wireline.response.xml.Services.Service.AccessPoints.AccessPoint.Data;

public class XMLSerializer {

  public XMLSerializer() {

  }

  public String marshal(ResponseWrapper response) {

    Services xmlServices = this.composeResponseFromResponseWrapper(response);

    StringWriter sw = new StringWriter();
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Services.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      // output pretty printed
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      jaxbMarshaller.marshal(xmlServices, sw);
    } catch (PropertyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return sw.toString();
  }

  private Services composeResponseFromResponseWrapper(ResponseWrapper response) {

    ObjectFactory of = new ObjectFactory();
    Services xmlServices = of.createServices();

    xmlServices.setProbeID(response.getProbeID());
    xmlServices.setResponseID(response.getResponseID());

    List<Service> serviceList = xmlServices.getService();

    for (ServiceWrapper service : response.getServices()) {
      Service xmlService = this.composeServiceFromServiceWrapper(service);
      serviceList.add(xmlService);
    }

    return xmlServices;

  }

  private Service composeServiceFromServiceWrapper(ServiceWrapper service) {
    ObjectFactory of = new ObjectFactory();
    Service xmlService = of.createServicesService();

    xmlService.setId(service.getId());
    xmlService.setServiceName(service.getServiceName());
    if (service.getTtl() != null)
      xmlService.setTtl(Integer.toString(service.getTtl().intValue()));
    else
      xmlService.setTtl("0");
    xmlService.setConsumability(service.getConsumability());
    xmlService.setContractDescription(service.getContractDescription());
    xmlService.setDescription(service.getDescription());
    xmlService.setContractID(service.getServiceContractID());

    for (AccessPoint ac : service.accessPoints) {
      ws.argo.wireline.response.xml.Services.Service.AccessPoints.AccessPoint ap = of.createServicesServiceAccessPointsAccessPoint();
      ap.setLabel(ac.label);
      ap.setIpAddress(ac.ipAddress);
      ap.setPort(ac.port);
      ap.setUrl(ac.url);
      Data xmlData = of.createServicesServiceAccessPointsAccessPointData();
      xmlData.setType(ac.dataType);
      xmlData.setValue(ac.data);
      ap.setData(xmlData);

      AccessPoints aps = xmlService.getAccessPoints();
      if (aps == null) {
        aps = of.createServicesServiceAccessPoints();
        xmlService.setAccessPoints(aps);
      }

      xmlService.getAccessPoints().getAccessPoint().add(ap);

    }
    return xmlService;

  }

  public ResponseWrapper unmarshal(String payload) throws ResponseParseException {

    return unmarshalXML(payload);

  }

  private ResponseWrapper unmarshalXML(String payload) throws ResponseParseException {

    Services xmlServices = parseResponsePayload(payload);

    ResponseWrapper response = new ResponseWrapper(xmlServices.getProbeID());
    response.setResponseID(xmlServices.getResponseID());

    for (Service xmlService : xmlServices.getService()) {
      ServiceWrapper service = new ServiceWrapper(xmlService.getId());

      service.setTtl(xmlService.getTtl());
      service.setConsumability(xmlService.getConsumability());
      service.setContractDescription(xmlService.getContractDescription());
      service.setDescription(xmlService.getDescription());
      service.setServiceContractID(xmlService.getContractID());
      service.setServiceName(xmlService.getServiceName());

      if (xmlService.getAccessPoints() != null) {
        for (ws.argo.wireline.response.xml.Services.Service.AccessPoints.AccessPoint ap : xmlService.getAccessPoints().getAccessPoint()) {
          service.addAccessPoint(ap.getLabel(), ap.getIpAddress(), ap.getPort(), ap.getUrl(), ap.getData().getType(), ap.getData().getValue());
        }
      }

      response.addResponse(service);

    }

    return response;
  }

  private static Services parseResponsePayload(String payload) throws ResponseParseException {

    JAXBContext jaxbContext;
    Services services = null;

    try {
      jaxbContext = JAXBContext.newInstance(Services.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      InputStream inputStream = new ByteArrayInputStream(payload.getBytes(Charset.forName("UTF-8")));

      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      SAXParser sp = spf.newSAXParser();
      XMLReader xmlReader = sp.getXMLReader();
      InputSource inputSource = new InputSource(inputStream);
      SAXSource saxSource = new SAXSource(xmlReader, inputSource);

      services = (Services) jaxbUnmarshaller.unmarshal(saxSource);

    } catch (JAXBException | FactoryConfigurationError | ParserConfigurationException | SAXException e) {
      throw new ResponseParseException(e);
    }

    return services;
  }

}
