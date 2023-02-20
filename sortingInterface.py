import pprint

import numpy as np
#1. 50개 곡, 감정 벡터 input, 10개의 곡 output 예상
def sorting(pre_tracklist,result):
    pre_resulting = []
    resulting = []
    abstracting = []
    #2. 50개 곡과 감정 벡터의 유클리디안 거리 구하기 numpy.norm 예상 됩니다.
    # 직선 거리
    for i in range(len(pre_tracklist)):
        pre = np.array([])
        before = pre_tracklist.iloc[i,-2:].to_numpy().astype(np.float64)
        after = 2*(before-0.5) - result
        # print(after)
        after = np.power(after,2).sum()
        # print(after)
        pre = np.append(pre,pre_tracklist.iloc[i,:])
        pre = np.append(pre,after)
        pre_resulting.append(pre.tolist())
    # print(resulting)
    for v in pre_resulting:
        if v[:6] not in abstracting:
            resulting.append(v)
            abstracting.append(v[:-1])
    # 3. 거리 기반 정렬(내림차순)
    resulting = sorted(resulting, key=lambda x:x[-1])
    set_result = []
    for v in resulting:
        set_result.append({'artist': v[0], 'track': v[1], 'audio':v[2], 'img':v[3], 'valence': v[4], 'arousal': v[5]})

    # 4. 정렬된 값으로 10개 추출하기('artist_name', 'track_name')
    return set_result[:10]



