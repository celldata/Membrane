<template>
  <div class="rule-container">
    <div class="rule-header">
        <!-- 字段选择 -->
        <div class="rule-filter-container">
            <el-select v-model="selectedRule" size="mini" @change="ruleChange" placeholder="请选择表名">
                <el-option v-for="r in rules"
                v-if="r.subRules.length > 0"
                :key="r.id"
                :label="r.label"
                :value="r.value"></el-option>
            </el-select>
            <el-select v-if="subRules !== null && subRules.length > 0" placeholder="请选择字段" v-model="selectedSubRule" size="mini" @change="subRuleChange">
                <el-option v-for="r in subRules"
                :key="r.id"
                :label="r.label"
                :value="r.value"></el-option>
            </el-select>
        </div>

        <!-- 操作符选择 -->
        <div class="rule-operator-container">
            <el-select v-model="query.selectedOperator" size="mini">
                <el-option v-for="operator in selectedRuleObj.operators" :value="operator.value" :key="operator.value" :label="operator.label">
                </el-option>
            </el-select>
        </div>
        <div class="rule-value-container">
          <el-input size="mini" v-if="selectedRuleObj.inputType === 'text'" type="text" v-model="query.value" clearable/>
          <el-input-number size="mini" v-if="selectedRuleObj.inputType === 'number'" v-model="query.value"></el-input-number>
          <el-date-picker size="mini" v-if="selectedRuleObj.inputType === 'date' || selectedRuleObj.inputType === 'datetime'" value-format="timestamp" :editable="false" :type="selectedRuleObj.inputType" v-model="query.value" />
          <el-checkbox-group size="mini" v-model="query.value" v-if="selectedRuleObj.inputType === 'checkbox'">
            <el-checkbox :label="choice.label" v-for="choice in selectedRuleObj.choices" :key="choice.label" :value="choice.value"></el-checkbox>
          </el-checkbox-group>
          <el-radio-group size="mini" v-model="query.value" v-if="selectedRuleObj.inputType === 'radio'">
            <el-radio :label="choice.label" v-for="choice in selectedRuleObj.choices" :key="choice.label" :value="choice.value"></el-radio>
          </el-radio-group>
        </div>
        <!-- 操作按钮 -->
        <div class="btn-group pull-right rule-actions">
            <i class="el-icon-delete" @click="remove"></i>
            <!-- <el-button size="mini" type="danger" icon="el-icon-close"  @click="remove">Delete</el-button> -->
        </div>
    </div>
  </div>
</template>

<script>
import deepClone from "../../../labs/deepClone";
import { attributeList } from "../../../api/api"
export default {
  name: "query-builder-rule",

  props: ["query", "index", "rules"],


  data() {
    return {
      selectedRuleObj: this.rules[0],
      selectedRule:this.query.value != null ? this.query.rule : null,
      selectedRule_label:null,
      selectedSubRule_protype:null,
      selectedSubRule: null,
      subRules: [],
      value1:null,
      attributes:[]
    };
  },
  methods: {
    remove: function() {
      this.$emit("child-deletion-requested", this.index);
    },
    // handleChange(val){
    //   this.value1 = val;
    //   let param = {
    //     configId: this.value1,
    //     pageSize: null,
    //     pageIndex: null
    //   };
    //   this.attributes = [];
    //   attributeList(param).then(res=>{
    //     if(res.err_CODE === 0){
    //       if(res.data.length > 0){
    //         res.data.forEach(item=>{
    //           let attr = {
    //             id: item.id,
    //             label: item.attributeName,
    //             type: item.attributeType
    //           };
    //           this.attributes.push(attr);
    //         })
    //       }

    //     }
    //   })
    // },
    // handleItemChange(val){
    // },
    // handleChange(val){
    //   console.log("val:",val);
    // },
    ruleChange: function() {
      const _this = this;
      this.query.value = null;
      this.subRules = [];
      this.selectedSubRule = null;
      this.rules.forEach(function(value) {
        if (value.id === _this.selectedRule) {
          _this.selectedRule_label = value.label;
          if (
            value.subRules !== undefined &&
            value.subRules !== null &&
            value.subRules.length > 0
          ) {
            _this.subRules = value.subRules;
          } else {
            _this.selectedRuleObj = value;
            _this.query.rule = _this.selectedRule;
            _this.initValue();
          }
        }
      });
      this.query.selectedOperator = this.selectedRuleObj.operators[0].value;
    },
    subRuleChange: function() {
      // console.log("二级菜单，表字段变化");
      const _this = this;
      this.query.value = null;
      this.subRules.forEach(function(value) {
        if (value.id === _this.selectedSubRule) {
          _this.selectedRuleObj = value;
          // console.log(value);
          _this.selectedSubRule_protype = value.attributeName;
          _this.query.selectedOperator =
            _this.selectedRuleObj.operators[0].value;
          // _this.query.rule = _this.selectedRule + "." + _this.selectedSubRule;
          _this.query.rule = _this.selectedRule_label + "." + _this.selectedSubRule_protype;
          _this.initValue();
        }
      });
    },
    initValue() {
      this.query.dateType = this.selectedRuleObj.dateType;
      if (this.query.value === null) {
        const updated_query = deepClone(this.query);
        if (this.selectedRuleObj.inputType === "checkbox") {
          updated_query.value = [];
        }
        if (
          this.selectedRuleObj.inputType === "select" ||
          this.selectedRuleObj.inputType === "radio"
        ) {
          updated_query.value = this.selectedRuleObj.choices[0];
        }
        if (
          this.selectedRuleObj.inputType === "time" ||
          this.selectedRuleObj.inputType === "date" ||
          this.selectedRuleObj.inputType === "datetime"
        ) {
          updated_query.value = Math.round(new Date());
        }
        this.$emit("update:query", deepClone(updated_query));
      }
    },
  },

  mounted() {
    // Set a default value for these types if one isn't provided already
    this.initValue();
    var _this = this;
    // console.log(_this.selectedRule);
    var selectedRuleCopy = _this.selectedRule;
    // console.log(selectedRuleCopy);
    var splitIndex = selectedRuleCopy != null ?  selectedRuleCopy.indexOf(".") : -1;
    if (splitIndex > -1) {
      _this.selectedRule = selectedRuleCopy.substring(0, splitIndex);
      _this.selectedSubRule = selectedRuleCopy.substring(splitIndex + 1);
      this.rules.forEach(function(rule) {
        if (rule.label === _this.selectedRule) {
          var isBreak = false;
          _this.subRules = rule.subRules;
          rule.subRules.forEach(function(subRule) {
            if (subRule.label === _this.selectedSubRule) {
              _this.selectedRuleObj = subRule;
              isBreak = true;
              return false;
            }
          });
          if (isBreak) {
            return false;
          }
        }
      });
    } else {
      this.rules.forEach(function(rule) {
        if (rule.value === _this.selectedRule) {
          _this.selectedRuleObj = rule;
          return false;
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
  .rule-container{
    .rule-header{
      display: flex;
      flex-direction: row;
      .rule-filter-container {
        display: flex;
        // .el-cascader--mini{
        //   width: 100%;
        // }
      }
      .rule-filter-container,.rule-operator-container,.rule-value-container{
        width:32%;
      }
      .rule-filter-container{
        width: 48%;
      }
      .rule-actions{
        width: 4%;
        i{
          font-weight: bold;
          color: #F56C6C;
        }
      }
    }
  }
</style>
