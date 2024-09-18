package com.github.aakumykov.kotlin_playground

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.github.aakumykov.android_dynamic_shortcuts_manager.DefaultShortcutsCreator
import com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager.DynamicShortcutManager
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.ShortcutsParser
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.extensions.getErrorMessage
import com.github.aakumykov.kotlin_playground.extensions.showAppProperties
import com.github.aakumykov.kotlin_playground.extensions.showToast
import javax.xml.parsers.SAXParserFactory


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var shortcuts: List<Shortcut>? = null
    private val dynamicShortcutManager by lazy { DynamicShortcutManager(this, 4) }
    private val shortcutsParser: ShortcutsParser by lazy { ShortcutsParser.getDefault(packageName, resources) }

    private lateinit var binding: ActivityMainBinding

    private val initialShortcuts: List<Shortcut> by lazy {
        try {
            shortcutsParser.parse(R.raw.shortcuts)
        } catch (e: Exception) {
            Log.e(TAG, e.getErrorMessage(), e)
            emptyList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ShortcutArrayAdapter(this)
        adapter.addAll(initialShortcuts)

        binding.listView.apply {
            this.adapter = adapter
            this.onItemClickListener = this@MainActivity
        }

        binding.appInfoButton.setOnClickListener { showAppProperties() }
        binding.clearLogButton.setOnClickListener { Logger.clear() }

        binding.button1.setOnClickListener { action1() }
        binding.button2.setOnClickListener { readShortcutsXML() }
        binding.button3.setOnClickListener { createShortcuts() }
        binding.button4.setOnClickListener { removeShortcuts() }
        binding.createDefaultShortcutsButton.setOnClickListener { createDefaultShortcuts() }

        createDefaultShortcuts()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }


    private fun createDefaultShortcuts() {
        try {
            DefaultShortcutsCreator.getDefault(this).initShortcuts(R.raw.shortcuts)
            Logger.d(TAG, "Ярлыки инициализированы")
        }
        catch (e: Exception) {
            Logger.e(TAG, e.getErrorMessage(), e)
        }
    }


    private fun action1() {
        val a: Int? = null

        // Так не работает
        a ?: {
            Logger.d(TAG, "a == null")
            showToast("a == null")
        }

        // А так работает
//        a ?: showToast("a == null")

        // И так работает
        a?.also {
            showToast("a != null")
        } ?: run {
            showToast("a == null")
        }
    }

    private fun readShortcutsXML() {

        try {
            shortcuts = ShortcutsParser(
                resources,
                ShortcutsXMLRawParser(
                    SAXParserFactory.newInstance().newSAXParser(),
                    ShortcutsSAXHandler()
                ),
                RawShortcutResolver(
                    ResourceResolver(
                        packageName,
                        resources
                    )
                )
            )
                .parse(R.raw.shortcuts)

            Logger.d(TAG,"XML ярлыков прочитан")
        }
        catch (e: Exception) {
            e.getErrorMessage().also { errorMsg ->
                showToast(errorMsg)
                Logger.d(TAG, errorMsg)
            }
        }
    }

    private fun createShortcuts() {

        if (null == shortcuts)
            readShortcutsXML()

        shortcuts?.also { list ->
            dynamicShortcutManager.removeDynamicShortcuts(list)

            if (!dynamicShortcutManager.isDynamicShortcutsSupported()) {
                showToast("Динамические ярлыки не поддерживаются!")
                return@also
            }

            val listOf4: MutableList<Shortcut> = list.toMutableList()

//            while (listOf4.size > DynamicShortcutManager.DEFAULT_MAX_SHORTCUTS_COUNT)
//                listOf4.removeAt(Random.nextInt(0, listOf4.size))

            try {
                dynamicShortcutManager.createDynamicShortcuts(listOf4)
                Logger.d(TAG,"Ярлыки обновлены (пересозданы?)")
            } catch (e: Exception) {
                e.getErrorMessage().also { errorMsg ->
                    showToast("Ошибка: $errorMsg")
                    Logger.e(TAG, errorMsg, e)
                }
            }

        } ?: run {
            showToast("Прочитайте файл ярлыков")
        }
    }

    private fun removeShortcuts() {

        if (null == shortcuts)
            readShortcutsXML()

        shortcuts?.also { list ->
            dynamicShortcutManager.removeDynamicShortcuts(list)
            Logger.d(TAG,"Ярлыки должны быть удалены")
        } ?: run {
            showToast("Прочитайте файл ярлыков")
        }
    }


    class ShortcutArrayAdapter(context: Context, @LayoutRes private val itemResource: Int) :
        ArrayAdapter<Shortcut>(context, android.R.layout.simple_list_item_multiple_choice)
    {
        private val inflater by lazy { LayoutInflater.from(context) }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view: View = inflater.inflate(itemResource, parent, false)
        }
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}