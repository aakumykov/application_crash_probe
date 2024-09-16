package com.github.aakumykov.kotlin_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager.DynamicShortcutManager
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.ShortcutsParser
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.extensions.showAppProperties
import com.github.aakumykov.kotlin_playground.extensions.showToast
import com.gitlab.aakumykov.exception_utils_module.ExceptionUtils
import javax.xml.parsers.SAXParserFactory
import kotlin.math.max
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var shortcuts: List<Shortcut>? = null
    private val dynamicShortcutManager by lazy {
        DynamicShortcutManager(
            this
        )
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.messages.observe(this) { messages -> binding.logView.text = messages.joinToString("\n") }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appInfoButton.setOnClickListener { showAppProperties() }
        binding.clearLogButton.setOnClickListener { Logger.clear() }

        binding.button1.setOnClickListener { action1() }
        binding.button2.setOnClickListener { readShortcutsXML() }
        binding.button3.setOnClickListener { createShortcuts() }
        binding.button4.setOnClickListener { removeShortcuts() }

        Logger.d(TAG, "Максимум ярлыков: ${dynamicShortcutManager.maxSupportedShortcutsCount}")
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
                .parse(resources, R.raw.shortcuts)

            Logger.d(TAG,"XML ярлыков прочитан")
        }
        catch (e: Exception) {
            ExceptionUtils.getErrorMessage(e).also { errorMsg ->
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

            val maxShortcutsCount = dynamicShortcutManager.maxSupportedShortcutsCount

            if (0 == maxShortcutsCount) {
                showToast("Динамические ярлыки не поддерживаются!")
                return@also
            }

            val listOf4: MutableList<Shortcut> = list.toMutableList();

            while (listOf4.size > maxShortcutsCount)
                listOf4.removeAt(Random.nextInt(0, listOf4.size))

            try {
                dynamicShortcutManager.createDynamicShortcuts(listOf4)
                Logger.d(TAG,"Ярлыки обновлены (пересозданы?)")
            } catch (e: Exception) {
                showToast("Ошибка")
                Logger.d(TAG, ExceptionUtils.getErrorMessage(e))
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



    private fun log(text:String) = Logger.d(TAG, text)


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}