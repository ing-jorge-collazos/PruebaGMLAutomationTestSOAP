import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)

public class AutomationTestWebservice {

    private final String URL_SOAP="http://ovc.catastro.meh.es";
    @Test
    public void addProvMun(){
        String resource="/ovcservweb/ovcswlocalizacionrc/ovccallejero.asmx";

        //Mapeo del header XML de la peticion
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type","text/xml;charset=UTF-8");
        headers.put("SOAPAction","http://tempuri.org/OVCServWeb/OVCCallejero/ConsultaMunicipio");

        Actor meTester = Actor.named("QA JorgeCollazos");

        meTester.can(CallAnApi.at(URL_SOAP));

        String bodyRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cat=\"http://www.catastro.meh.es/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <cat:Provincia>PONTEVEDRA</cat:Provincia>\n" +
                "      <cat:Municipio>VIGO</cat:Municipio>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        meTester.attemptsTo(
                Post.to(resource)
                        .with(
                                req -> req.headers(headers)
                                        .body(bodyRequest)
                        )
        );

        System.out.println(LastResponse.received().answeredBy(meTester)
                .asString());

    }
}
