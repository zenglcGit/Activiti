/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.spring.boot.process;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.spring.boot.security.util.SecurityUtil;
import org.activiti.spring.boot.test.util.ProcessCleanUpUtil;
import org.activiti.spring.boot.test.util.TaskCleanUpUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProcessRuntimeTasksIT {

  private static final String SINGLE_TASK_PROCESS = "SingleTaskProcess";

  @Autowired private ProcessRuntime processRuntime;

  @Autowired private TaskRuntime taskRuntime;

  @Autowired private SecurityUtil securityUtil;

  @Autowired private ProcessCleanUpUtil processCleanUpUtil;

  @Autowired private TaskCleanUpUtil taskCleanUpUtil;

  @AfterEach
  public void cleanUp() {
    processCleanUpUtil.cleanUpWithAdmin();
    taskCleanUpUtil.cleanUpWithAdmin();
  }

  @Test
  public void should_taskAlwaysHaveAppVersion() {
    securityUtil.logInAs("garth");

    ProcessInstance processInstance =
        processRuntime.start(
            ProcessPayloadBuilder.start().withProcessDefinitionKey(SINGLE_TASK_PROCESS).build());

    Page<Task> tasks =
        taskRuntime.tasks(
            Pageable.of(0, 50),
            TaskPayloadBuilder.tasks().withProcessInstanceId(processInstance.getId()).build());

    assertThat(tasks.getContent()).hasSize(1);

    Task result = tasks.getContent().get(0);

    assertThat(result.getName()).isEqualTo("my-task");
    assertThat(result.getAppVersion()).isEqualTo("1");
  }
}
