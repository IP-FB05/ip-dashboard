package de.fhaachen.praxissemester;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

public class BetreuerFieldValidator implements FormFieldValidator {


  @Override
  public boolean validate(Object value, FormFieldValidatorContext ctx) {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    IdentityService identityService = processEngine.getIdentityService();
    List<User> user = identityService.createUserQuery().userId((String) value).memberOfGroup("Professoren").list();
    return user.size() > 0;
  }

}
