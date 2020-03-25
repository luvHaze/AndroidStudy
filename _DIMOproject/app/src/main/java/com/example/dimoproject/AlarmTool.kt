package com.example.dimoproject

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.dimoproject.Data.MemoDAO
import io.realm.Realm
import java.util.*

//알람을 추가 또는 삭제하는 기능 및
//BroadCastReceiver 기능을 겸함으로서 알람 신호를 받는 역할 수행

class AlarmTool : BroadcastReceiver() {

    companion object {
        private const val ACITON_RUN_ALARM = "RUN_ALARM"

        private fun createAlarmIntent(context: Context, id: String): PendingIntent {
            //AlarmTool 클래스를 목적지로 하는 Intent 생성
            //이 클래스가 Receiver의 역할도 하기 때문
            val intent = Intent(context, AlarmTool::class.java)
            // intent 의 data에 메모 id를 받아 Uri로 추가함 (시스템에서 Intent를 구별하는 기준이 됨)
            intent.data = Uri.parse("id:" + id)
            intent.putExtra("MEMO_ID", id)
            intent.action = ACITON_RUN_ALARM

            return PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        // create AlarmIntent 함수로 알람 Intent를 생성하여 AlarmManager에 알람을 설정하는 함수
        fun addAlarm(context: Context, id: String, alarmTime: Date) {
            val alarmIntent = createAlarmIntent(context, id)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.time, alarmIntent)
        }

        fun deleteAlarm(context: Context, id: String) {
            val alarmIntent = createAlarmIntent(context, id)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(alarmIntent)
        }


    }

    // BroadCastReceiver가 broadcast를 받았을때 동작하는 onReceive
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {

            AlarmTool.ACITON_RUN_ALARM -> {

                val memoId = intent.getStringExtra("MEMO_ID")
                val realm = Realm.getDefaultInstance()
                val memoData = MemoDAO(realm).selectMemo(memoId)

                //Notification에 연결할 Intent를 생성함 (Noti 누를 경우 해당 메모 상세화면 이동)
                val notificationIntent = Intent(context, DetailActivity::class.java)
                notificationIntent.putExtra("MEMO_ID", memoId)

                //intent를 PendingIntent 의 Activity형태로 만들어 반환
                val pendingIntent = PendingIntent.getActivity(
                    context, 0
                    , notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
                )

                //notification의 ChannelId를 "alarm"으로 서지정함
                val builder = NotificationCompat.Builder(context!!, "alarm")
                    .setContentTitle(memoData.title)
                    .setContentText(memoData.content)
                    .setContentIntent(pendingIntent)        // 눌렀을때 상세화면 이동하도록 pendingIntent 연결시킴
                    .setAutoCancel(true)

                //시스템의 NotificationManager를 받아옴
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // OREO 이상의 버전에서는 mipmap 아이콘을 사용할 수 없어 따로 저장해야 한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // OREO 이상 버전에서는 NotificationChannel을 지정하는 코드가 필수적
                    builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    val channel = NotificationChannel(
                        "alarm", "알람 메시지"
                        , NotificationManager.IMPORTANCE_HIGH
                    )
                    notificationManager.createNotificationChannel(channel)
                    // 여러개의 Noti Channel을 사용자에게
                    // 제공하여 채널별로 선택적으로 사용 또는 차단하게 하는 기능
                } else {
                    builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                }
                //Noti 매니저를 통해 알림을 표시한다.
                //첫번째 파라미터인 id 값을 변경하면 여러개 띄울 수 있다.
                notificationManager.notify(1, builder.build())
            }

            // 기기부팅 시 받을 수 있는 broadcast의 action
            Intent.ACTION_BOOT_COMPLETED -> {
                // MemoDAO에 만들어 두었던 활성화된 알람을 가져오는 함수 사용
                val realm=Realm.getDefaultInstance()
                val activeAlarms = MemoDAO(realm).getActiveAlarms()

                //알람을 재 등록해준다.
                for(memoData in activeAlarms){
                    addAlarm(context!!,memoData.id,memoData.alarmTime)
                }
            }


        }
    }


}