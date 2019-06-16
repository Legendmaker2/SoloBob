package com.codefair.solobob;

import java.util.List;

public class ScheduleListTO {

    private ScheduleInfoTO scheduleInfoTO;
    private List<ApplyInfoTO> applyInfoTOList;

    public ScheduleInfoTO getScheduleInfoTO() {
        return scheduleInfoTO;
    }

    public void setScheduleInfoTO(ScheduleInfoTO scheduleInfoTO) {
        this.scheduleInfoTO = scheduleInfoTO;
    }

    public List<ApplyInfoTO> getApplyInfoTOList() {
        return applyInfoTOList;
    }

    public void setApplyInfoTOList(List<ApplyInfoTO> applyInfoTOList) {
        this.applyInfoTOList = applyInfoTOList;
    }
}
