<template>
  <div class="query-builder form-inline">
    <query-builder-group
      :index="0"
      :query.sync="query"
      :ruleTypes="ruleTypes"
      :rules="mergedRules"
      :maxDepth="maxDepth"
      :depth="depth"
      type="query-builder-group"
      ></query-builder-group>
  </div>
</template>

<script>
import QueryBuilderGroup from "./components/QueryBuilderGroup.vue";
import deepClone from "../../labs/deepClone.js";

export default {
  name: "vue-query-builder",

  components: {
    QueryBuilderGroup
  },

  props: {
    rules: Array,
    maxDepth: {
      type: Number,
      default: 8,
      validator: function(value) {
        return value >= 1;
      }
    },
    value: Object
  },

  data() {
    return {
      depth: 1,
      query: {
        logicalOperator: "AND",
        children: []
      },
      ruleTypes: {
        text: {
          operators: [
            { label: "=", value: "=" },
            { label: "<>", value: "<>" },
            { label: "<", value: "<" },
            { label: "<=", value: "<=" },
            { label: ">=", value: ">=" },
            { label: "LIKE", value: "LIKE" },
            { label: "IN", value: "IN" }
          ],
          inputType: "text",
          id: "text-field",
          dataType: "string"
        },
        numeric: {
          operators: [
            { label: "=", value: "=" },
            { label: "<>", value: "<>" },
            { label: "<", value: "<" },
            { label: "<=", value: "<=" },
            { label: ">=", value: ">=" },
            { label: "LIKE", value: "LIKE" },
            { label: "IN", value: "IN" }
          ],
          inputType: "number",
          id: "number-field",
          dataType: "numeric"
        },
        date: {
          operators: [
            { label: "=", value: "=" },
            { label: "<>", value: "<>" },
            { label: "<", value: "<" },
            { label: "<=", value: "<=" },
            { label: ">=", value: ">=" },
            { label: "LIKE", value: "LIKE" },
            { label: "IN", value: "IN" }
          ],
          inputType: "date",
          id: "date-field",
          dataType: "date"
        },
        datetime: {
          operators: [
            { label: "=", value: "=" },
            { label: "<>", value: "<>" },
            { label: "<", value: "<" },
            { label: "<=", value: "<=" },
            { label: ">=", value: ">=" },
            { label: "LIKE", value: "LIKE" },
            { label: "IN", value: "IN" }
          ],
          inputType: "datetime",
          id: "datetime-field",
          dataType: "datetime"
        },
        radio: {
          operators: [
            { label: "等于", value: "EQUALS" },
            { label: "不等于", value: "NOTEQUALS" }
          ],
          choices: [],
          inputType: "radio",
          id: "radio-field",
          dataType: "boolean"
        },
        checkbox: {
          operators: [
            { label: "等于", value: "EQUALS" },
            { label: "不等于", value: "NOTEQUALS" },
            { label: "包含于", value: "IN" },
            { label: "不包含于", value: "NOTIN" }
          ],
          choices: [],
          inputType: "checkbox",
          id: "checkbox-field",
          dataType: "array"
        },
        select: {
          operators: [
            { label: "等于", value: "EQUALS" },
            { label: "不等于", value: "NOTEQUALS" }
          ],
          choices: [],
          inputType: "select",
          id: "select-field",
          dataType: "object"
        }
      },
    };
  },

  computed: {
    mergedRules() {
      var mergedRules = [];
      var vm = this;

      vm.rules.forEach(function(rule) {
        if (rule.subRules !== undefined) {
          var mergedSubRules = [];
          rule.subRules.forEach(function(subRule) {
            if (typeof vm.ruleTypes[subRule.type] !== "undefined") {
              mergedSubRules.push(
                Object.assign({}, vm.ruleTypes[subRule.type], subRule)
              );
            } else {
              mergedSubRules.push(subRule);
            }
          });
          rule.subRules = mergedSubRules;
        }
        if (typeof vm.ruleTypes[rule.type] !== "undefined") {
          mergedRules.push(Object.assign({}, vm.ruleTypes[rule.type], rule));
        } else {
          mergedRules.push(rule);
        }
      });
      return mergedRules;
    }
  },

  mounted() {
    this.$watch(
      "query",
      newQuery => {
        this.$emit("input", deepClone(newQuery));
      },
      {
        deep: true
      }
    );

    if (typeof this.$options.propsData.value !== "undefined") {
      this.query = Object.assign(this.query, this.$options.propsData.value);
    }
  }
};
</script>

<style>
/*!
 * jQuery QueryBuilder 2.5.2
 * Copyright 2014-2018 Damien "Mistic" Sorel (http://www.strangeplanet.fr)
 * Licensed under MIT (https://opensource.org/licenses/MIT)
 */
 .query-builder{
   padding: 0;
   max-height: 400px;
   overflow-y: auto;
 }
.query-builder .rule-container,
.query-builder .rule-placeholder,
.query-builder .rules-group-container {
  position: relative;
  margin: 4px 0;
  border-radius: 5px;
  padding: 5px;
  border: 1px solid #DCDFE6;
  background: rgba(238, 244, 254, 1)
}

.query-builder .drag-handle,
.query-builder .error-container,
.query-builder .rule-container .rule-filter-container,
.query-builder .rule-container .rule-operator-container,
.query-builder .rule-container .rule-value-container {
  display: inline-block;
  margin: 0 5px 0 0;
  vertical-align: middle;
}

.query-builder .rules-group-container {
  padding: 10px;
  padding-bottom: 6px;
  border: 1px solid #DCDFE6;
  background: rgba(238, 244, 254, 1)
}

.query-builder .rules-group-header {
  margin-bottom: 10px;
}

.query-builder .rules-group-header .group-conditions .btn.readonly:not(.active),
.query-builder .rules-group-header .group-conditions input[name$="_cond"] {
  border: 0;
  clip: rect(0 0 0 0);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
  width: 1px;
  white-space: nowrap;
}

.query-builder .rules-group-header .group-conditions .btn.readonly {
  border-radius: 3px;
}

.query-builder .rules-list {
  list-style: none;
  padding: 0 0 0 15px;
  margin: 0;
}

.query-builder .rule-value-container {
  border-left: 1px solid #ddd;
  padding-left: 5px;
}

.query-builder .rule-value-container label {
  margin-bottom: 0;
  font-weight: 400;
}

.query-builder .rule-value-container label.block {
  display: block;
}
.query-builder .rule-value-container .el-input .el-input__inner,.query-builder .rule-actions .el-button{
  height: 33px;
}

.query-builder .error-container {
  display: none;
  cursor: help;
  color: red;
}

.query-builder .has-error {
  background-color: #fdd;
  border-color: #f99;
}

.query-builder .has-error .error-container {
  display: inline-block !important;
}

.query-builder .rules-list > ::after,
.query-builder .rules-list > ::before {
  content: "";
  position: absolute;
  left: -10px;
  width: 10px;
  height: calc(50% + 4px);
  border-color: #ccc;
  border-style: solid;
}

.query-builder .rules-list > ::before {
  top: -4px;
  border-width: 0 0 2px 2px;
}

.query-builder .rules-list > ::after {
  top: 50%;
  border-width: 0 0 0 2px;
}

.query-builder .rules-list > :first-child::before {
  top: -12px;
  height: calc(50% + 14px);
}

.query-builder .rules-list > :last-child::before {
  border-radius: 0 0 0 4px;
}

.query-builder .rules-list > :last-child::after {
  display: none;
}

.query-builder.bt-checkbox-glyphicons
  .checkbox
  input[type="checkbox"]:checked
  + label::after {
  font-family: "Glyphicons Halflings";
  content: "\e013";
}

.query-builder.bt-checkbox-glyphicons .checkbox label::after {
  padding-left: 4px;
  padding-top: 2px;
  font-size: 9px;
}

.query-builder .error-container + .tooltip .tooltip-inner {
  color: #f99 !important;
}

.query-builder p.filter-description {
  margin: 5px 0 0 0;
  background: #d9edf7;
  border: 1px solid #bce8f1;
  color: #31708f;
  border-radius: 5px;
  padding: 2.5px 5px;
  font-size: 0.8em;
}

.query-builder .rules-group-header [data-invert] {
  margin-left: 5px;
}

.query-builder .drag-handle {
  cursor: move;
  vertical-align: middle;
  margin-left: 5px;
}

.query-builder .dragging {
  position: fixed;
  opacity: 0.5;
  z-index: 100;
}

.query-builder .dragging::after,
.query-builder .dragging::before {
  display: none;
}

.query-builder .rule-placeholder {
  border: 1px dashed #bbb;
  opacity: 0.7;
}
.pull-right {
  float: right !important;
}
.has-error {
  background-color: #fdd;
  border-color: #f99;
}
</style>
