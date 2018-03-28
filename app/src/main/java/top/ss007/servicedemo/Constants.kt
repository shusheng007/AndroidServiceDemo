package top.ss007.servicedemo

/**
 * Copyright (C) 2018 科技发展有限公司
 * 完全享有此软件的著作权，违者必究
 *
 * @author       ben
 * @modifier
 * @createDate   2018/3/27 13:47
 * @version      1.0
 * @description
 */
class Constants {

    interface ACTION {
        companion object {
            val MAIN_ACTION = "com.marothiatechs.foregroundservice.action.main"
            val INIT_ACTION = "com.marothiatechs.foregroundservice.action.init"
            val PREV_ACTION = "com.marothiatechs.foregroundservice.action.prev"
            val PLAY_ACTION = "com.marothiatechs.foregroundservice.action.play"
            val NEXT_ACTION = "com.marothiatechs.foregroundservice.action.next"
            val STARTFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.startforeground"
            val STOPFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.stopforeground"
        }
    }

    interface NOTIFICATION_ID {
        companion object {
            val FOREGROUND_SERVICE = 101
        }
    }
}