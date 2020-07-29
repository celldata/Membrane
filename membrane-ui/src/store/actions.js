export function modifyCurrentId({commit}, arr) { // commit 提交；
    return commit ('modifyCurrentId', arr)
}


export function modifySpaceTemplateListToDetailInfo({},info) {
    return commit ('modifySpaceTemplateListToDetailInfo', info)
}

export function modifyIsBackFromDetailPage({}, info){
    return commit ('modifyIsBackFromDetailPage', info)
}

export function modifySnapData({},info){
    return commit ('modifySnapData', info)
}

