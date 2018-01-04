package com.github.persistence;

import com.github.schmittjoaopedro.App;
import com.github.schmittjoaopedro.domain.*;
import com.github.schmittjoaopedro.repository.ObjectValueRepository;
import com.github.schmittjoaopedro.service.AttributeService;
import com.github.schmittjoaopedro.service.ClassTypeService;
import com.github.schmittjoaopedro.service.ObjectInstanceService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = App.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FullPersistenceTest {

    @Resource
    private AttributeService attributeService;

    @Resource
    private ObjectInstanceService objectInstanceService;

    @Resource
    private ClassTypeService classTypeService;

    @Resource
    private ObjectValueRepository objectValueRepository;

    @Test
    public void _clearDatabase() {
        objectInstanceService.deleteAll();
        classTypeService.deleteAll();
        attributeService.deleteAll();
    }

    @Test
    public void createFullApplication() {

        Attribute C1 = new Attribute();
        C1.setName("CHARACTERISTIC_1");
        C1.setType(AttributeType.String);
        C1 = attributeService.save(C1);

        AttributeDescription C1DPT = new AttributeDescription();
        C1DPT.setLanguage("pt");
        C1DPT.setDescription("Característica Um");
        C1.getDescriptions().add(C1DPT);
        AttributeDescription C1EN = new AttributeDescription();
        C1EN.setLanguage("en");
        C1EN.setDescription("Attribute One");
        C1.getDescriptions().add(C1EN);
        C1 = attributeService.save(C1);

        C1.getDescriptions().stream().filter(item -> item.getLanguage().equals("pt")).findFirst().get().setDescription("Característica 1");
        C1.getDescriptions().stream().filter(item -> item.getLanguage().equals("en")).findFirst().get().setDescription("Attribute 1");
        C1 = attributeService.save(C1);

        AttributeValue C1V1 = new AttributeValue();
        C1V1.setAttribute(C1);
        C1V1.setValue("00001");
        C1.getValues().add(C1V1);
        AttributeValue C1V2 = new AttributeValue();
        C1V2.setAttribute(C1);
        C1V2.setValue("00002");
        AttributeValueDescription C1V2DPT = new AttributeValueDescription();
        C1V2DPT.setLanguage("pt");
        C1V2DPT.setDescription("Valor 00002");
        C1V2.getDescriptions().add(C1V2DPT);
        AttributeValueDescription C1V2DEN = new AttributeValueDescription();
        C1V2DEN.setLanguage("en");
        C1V2DEN.setDescription("Value 00002");
        C1V2.getDescriptions().add(C1V2DEN);
        C1.getValues().add(C1V2);
        C1 = attributeService.save(C1);

        C1.getValues().stream().filter(item -> item.getValue().equals("00002")).findFirst().get().getDescriptions().stream().filter(item -> item.getLanguage().equals("pt")).findFirst().get().setDescription("Valor 2");
        C1.getValues().stream().filter(item -> item.getValue().equals("00002")).findFirst().get().getDescriptions().stream().filter(item -> item.getLanguage().equals("en")).findFirst().get().setDescription("Value 2");
        C1 = attributeService.save(C1);

        ClassType OT1 = new ClassType();
        OT1.setName("OBJECT_TYPE_1");
        OT1.getAttributes().add(C1);
        OT1 = classTypeService.save(OT1);

        ObjectInstance O1 = new ObjectInstance();
        O1.setClassType(OT1);
        ObjectValue O1V1 = new ObjectValue();
        O1V1.setValue("00001");
        O1V1.setAttribute(C1);
        O1V1.setObjectInstance(O1);
        O1.getValues().add(O1V1);
        ObjectInstance O2 = new ObjectInstance();
        O2.setClassType(OT1);
        ObjectValue O2V1 = new ObjectValue();
        O2V1.setValue("00001");
        O2V1.setAttribute(C1);
        O2V1.setObjectInstance(O2);
        O2.getValues().add(O2V1);
        O1.getChildren().add(O2);
        ObjectInstance O3 = new ObjectInstance();
        O3.setClassType(OT1);
        ObjectValue O3V1 = new ObjectValue();
        O3V1.setValue("00001");
        O3V1.setAttribute(C1);
        O3V1.setObjectInstance(O3);
        O3.getValues().add(O3V1);
        O2.getChildren().add(O3);
        O1 = objectInstanceService.save(O1);

        ObjectValue OV = objectValueRepository.findById(O1.getValues().get(0).getId()).get();
        OV.setValue("00002");
        OV.setAttribute(null);
        objectValueRepository.save(OV);

        Assert.assertTrue(true);
    }

}
