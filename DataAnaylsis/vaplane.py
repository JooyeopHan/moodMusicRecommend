from DataAnaylsis import museProcess

def MakePlane():
    muse = museProcess.readData()
    museProcess.process(muse)
    result = museProcess.VAPlane(muse)
    return result

# 평면 -> 곡 매핑 -> 사용자 감정 벡터 생산 -> 거리 기반 유사한
# 사용자 감정 벡터 생산 -> 거리 기반 유사한 => model 작업
# 감정 인지 모델 제작
#
# luxand face api