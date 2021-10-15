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

package org.activiti.engine.impl.variable;

/** */
public class StringType implements VariableType {

  private final int maxLength;

  public StringType(int maxLength) {
    this.maxLength = maxLength;
  }

  public String getTypeName() {
    return "string";
  }

  public boolean isCachable() {
    return true;
  }

  public Object getValue(ValueFields valueFields) {
    return valueFields.getTextValue();
  }

  public void setValue(Object value, ValueFields valueFields) {
    valueFields.setTextValue((String) value);
  }

  public boolean isAbleToStore(Object value) {
    if (value == null) {
      return true;
    }
    if (String.class.isAssignableFrom(value.getClass())) {
      String stringValue = (String) value;
      return stringValue.length() <= maxLength;
    }
    return false;
  }
}
