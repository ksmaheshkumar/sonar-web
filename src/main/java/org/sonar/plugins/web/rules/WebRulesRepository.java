/*
 * SonarQube Web Plugin
 * Copyright (C) 2010 SonarSource and Matthijs Galesloot
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.web.rules;

import com.google.common.collect.Lists;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.plugins.web.api.WebConstants;
import org.sonar.plugins.web.checks.RuleTags;
import org.sonar.plugins.web.checks.WebRule;

import java.util.List;

public final class WebRulesRepository extends RuleRepository {

  public static final String REPOSITORY_KEY = WebConstants.LANGUAGE_NAME;

  public WebRulesRepository() {
    super(REPOSITORY_KEY, WebConstants.LANGUAGE_KEY);
    setName("SonarQube");
  }

  @Override
  public List<Rule> createRules() {
    List<Rule> result = Lists.newArrayList();
    for (Class ruleClass : CheckClasses.getCheckClasses()) {
      if (AnnotationUtils.getAnnotation(ruleClass, WebRule.class) != null) {
        result.add(RuleRepositoryHelper.createRule(
          REPOSITORY_KEY,
          ruleClass,
          AnnotationUtils.getAnnotation(ruleClass, org.sonar.check.Rule.class),
          AnnotationUtils.getAnnotation(ruleClass, RuleTags.class)
        ));
      }
    }
    return result;
  }

}
