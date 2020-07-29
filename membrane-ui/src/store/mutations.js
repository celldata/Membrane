
// 提交 mutations是更改Vuex状态的唯一合法方法
export const modifyCurrentId = (state,arr) => { 
    state.nationsArrId = arr 
}


export const modifySpaceTemplateListToDetailInfo = (state,info) => {
    state.space_template_list_to_detail_info = info
}

export const modifyIsBackFromDetailPage = (state, info) => {
    state.isBackFromDetailPage = info
}

export const modifySnapData = (state, info) => {
    state.snap_data = info
}
