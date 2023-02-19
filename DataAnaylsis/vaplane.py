from DataAnaylsis import museProcess

def MakePlane():
    muse = museProcess.readData()
    museProcess.process(muse)
    result = museProcess.VAPlane(muse)
    return result
