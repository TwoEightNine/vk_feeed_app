package global.msnthrp.feeed.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import global.msnthrp.feeed.utils.l
import java.lang.Exception
import javax.inject.Inject

class DbHelper @Inject constructor(context: Context)
    : OrmLiteSqliteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "feeed.db"
        const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?, conn: ConnectionSource?) {
        try {
            createAll(conn)
        } catch (e: Exception) {
            l("db onCreate: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, conn: ConnectionSource?, old: Int, new: Int) {
//        migrate(conn, Account::class.java, accountDao)
//        migrate(conn, Contact::class.java, contactDao)
//        migrate(conn, Dialog::class.java, dialogsDao)
//        TableUtils.createTable(conn, ChatMessage::class.java)
    }

    private fun <T, ID> migrate(conn: ConnectionSource?,
                                              dataClass: Class<T>,
                                              dao: BaseDaoImpl<T, ID>
    ) {
        val all = dao.queryForAll()
        TableUtils.dropTable<T, ID>(conn, dataClass, true)
        TableUtils.createTable(conn, dataClass)
        dao.create(all)
    }

    private fun createAll(conn: ConnectionSource?) {
//        TableUtils.createTable(conn, Account::class.java)
//        TableUtils.createTable(conn, Contact::class.java)
//        TableUtils.createTable(conn, Dialog::class.java)
//        TableUtils.createTable(conn, ChatMessage::class.java)
    }

    private fun dropAll(conn: ConnectionSource?) {
//        TableUtils.dropTable<Account, Int>(conn, Account::class.java, true)
//        TableUtils.dropTable<Contact, Int>(conn, Contact::class.java, true)
//        TableUtils.dropTable<Dialog, Int>(conn, Dialog::class.java, true)
//        TableUtils.dropTable<ChatMessage, Int>(conn, ChatMessage::class.java, true)
    }
}