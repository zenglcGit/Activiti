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
package org.activiti.api.process.runtime;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.DeleteProcessPayload;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.activiti.api.process.model.payloads.RemoveProcessVariablesPayload;
import org.activiti.api.process.model.payloads.ResumeProcessPayload;
import org.activiti.api.process.model.payloads.SetProcessVariablesPayload;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartMessagePayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.model.payloads.SuspendProcessPayload;
import org.activiti.api.process.model.payloads.UpdateProcessPayload;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;

public interface ProcessAdminRuntime {

  /** Get process definition by key or id (where the ID is autogenerated at deployment time) */
  ProcessDefinition processDefinition(String processDefinitionId);

  /** Get all process definitions by pages */
  Page<ProcessDefinition> processDefinitions(Pageable pageable);

  /** Get all process definitions by pages using payload filters */
  Page<ProcessDefinition> processDefinitions(
      Pageable pageable, GetProcessDefinitionsPayload getProcessDefinitionsPayload);

  /** Start a new Process Instance based on the payload parameters */
  ProcessInstance start(StartProcessPayload startProcessPayload);

  /**
   * Get all process instances by pages - Notice that only in-flight or suspended processes will be
   * returned here - For already completed process instance check at the query service
   */
  Page<ProcessInstance> processInstances(Pageable pageable);

  /**
   * Get all process instances by pages filtering by - Notice that only in-flight or suspended
   * processes will be returned here - For already completed process instance check at the query
   * service
   */
  Page<ProcessInstance> processInstances(
      Pageable pageable, GetProcessInstancesPayload getProcessInstancesPayload);

  /** Get Process Instance by id */
  ProcessInstance processInstance(String processInstanceId);

  /** Delete a Process Instance */
  ProcessInstance delete(DeleteProcessPayload deleteProcessPayload);

  /** Signal */
  void signal(SignalPayload signalPayload);

  /** Suspend a process instance */
  ProcessInstance suspend(SuspendProcessPayload suspendProcessPayload);

  /** Resume a suspended process instance */
  ProcessInstance resume(ResumeProcessPayload resumeProcessPayload);

  /** Update a Process Instance */
  ProcessInstance update(UpdateProcessPayload updateProcessPayload);

  void setVariables(SetProcessVariablesPayload setProcessVariablesPayload);

  void removeVariables(RemoveProcessVariablesPayload removeProcessVariablesPayload);

  void receive(ReceiveMessagePayload messagePayload);

  ProcessInstance start(StartMessagePayload messagePayload);
}
