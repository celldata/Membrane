<template>
  <div id="query-builder">
    <vue-query-builder :rules="resource"></vue-query-builder>
  </div>
</template>

<script>
import {
  resourceList
} from "../../api/api"
import vueQueryBuilder from './VueQueryBuilder'
export default {
  components:{vueQueryBuilder},
  data(){
    return{
      page:{
        clientId: 1,
        type: null,
        pageIndex: null,
        pageSize: null
      },
      flag:0,
      rootObj:{
        left:null,
        operator:null,
        right:null
      },
      left:{},
      right:{},
      rules:[
          {
            type: "text",
            id: "project-name",
            label: "rr",
          },
          {
            type: "radio",
            id: "plan-type",
            label: "Plan Type",
            choices: [
              {label: "Standard", value: "standard"},
              {label: "Premium", value: "premium"}
            ]
          },
          {
            type: "checkbox",
            id: "area",
            label: "area",
            choices: [
              {label: "华南", value: "small"},
              {label: "华中", value: "medium"},
              {label: "华北", value: "large"}
            ]
          },
          {
            type: "text",
            id: "date",
            inputType:"date",
            label: "dateTime",
            operands: ['Start Date', 'End Date']
          },
          {
            type: "select",
            id: 'select',
            label: 'Color',
            operators: ['=', '<>'],
            choices: [
              {label: "red", value: 'Red'},
              {label: "orange", value: 'Orange'},
              {label: "yellow", value: 'Yellow'},
              {label: "green", value: 'Green'},
              {label: "blue", value: 'Blue'},
              {label: "indigo", value: 'Indigo'},
              {label: "violet", value: 'Violet'},
            ]
          },
      ],
      arr:[],
      count:0,
      resource:null,
    }
  },
  methods:{
    getResource(){
      /**
       * @description 获取资源列表
       */
      resourceList(this.page).then(res=>{
        if(res.err_CODE === 0){
          console.log(res);
        }else{
          this.$$message.error(res.err_MESSAGE);
        }
      })
    },
    Tree(){
      // console.log(this.rules);
    },
    sqlStatements(root){
      let obj = {};
      if(root.children.length === 0){
        return '暂无数据';
      }
      // 数组长度为1时
      if(root.children.length === 1){
        // return this.sqlNodeStatements(root.children[0]);
      }else{
        this.rootObj.operator = root.logicalOperator;
       // 数组长度>1,从中间开始分解，并构造左边数组和右边数组
        this.sliceHandler(root.children);
      }
    },
    sliceHandler(){

    },
    BinarySearchTree(){
      let node = function(ele){
        this.data = ele;
        this.lChild = null;
        this.rChild = null;
      };
      let root = null;
    },
    insertNode(rnode,newNode){
      if(rnode.left == null){
        rnode.left = newNode;
      }else{
        this.insertNode(rnode.left,newNode);
      }

      if(rnode.right == null){
        rnode.right = newNode;
      }else{
        this.insertNode(rnode.right,newNode);
      }
    }
  },
  mounted(){
  // let param = {
  //   logicalOperator: "all",
  //   children:[
  //     {
  //           type: "query-builder-group",
  //           query: {
  //             logicalOperator: "any",
  //             children: [
  //               {
  //                 type: "query-builder-rule",
  //                 query: {
  //                   rule: "vegetable",
  //                   operator: "contains",
  //                   operand: "Vegetable",
  //                   value: null
  //                 }
  //               },
  //               {
  //                 type: "query-builder-rule",
  //                 query: {
  //                   rule: "fruit",
  //                   operand: "Fruit",
  //                   value: "banana"
  //                 }
  //               },
  //               // {
  //               //   type:'query-builder-group',
  //               //   query:{
  //               //     logicalOperator: "and",
  //               //     children:[
  //               //       {
  //               //         type: "query-builder-rule",
  //               //         query: {
  //               //           rule: "内部",
  //               //           operator: "contains",
  //               //           operand: "内部",
  //               //           value: 12
  //               //         }
  //               //       },
  //               //     ]
  //               //   }
  //               // }

  //             ]
  //           }
  //     },
	//     {
	//       type: "query-builder-rule",
	//       query: {
	//         rule: "vegetable",
	//         operator: "equals",
	//         operand: "Vegetable",
	//         value: "14"
	//       }
  //     },
	//     {
	//       type: "query-builder-rule",
	//       query: {
	//         rule: "vegetable1",
	//         operator: "equals",
	//         operand: "Vegetable1",
	//         value: "15"
	//       }
	//     }
  //   ]
  // }
  // this.sqlStatements(param);
  this.getResource();
  // this.main(param.children);
  // console.log(this.sqlStatements(param));
  }
}
</script>
