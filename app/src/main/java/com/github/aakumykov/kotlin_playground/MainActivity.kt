package com.github.aakumykov.kotlin_playground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.dynamic_shortcut_manager.DynamicShortcutManager
import com.github.aakumykov.kotlin_playground.extensions.showAppProperties
import com.github.aakumykov.kotlin_playground.extensions.showToast
import com.github.aakumykov.kotlin_playground.shortcuts_parser.utils.ShortcutsSAXHandler
import com.github.aakumykov.kotlin_playground.shortcuts_parser.ShortcutsParser
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut
import com.github.aakumykov.kotlin_playground.shortcuts_parser.utils.ShortcutsXMLRawParser
import com.github.aakumykov.kotlin_playground.shortcuts_parser.utils.RawShortcutResolver
import com.github.aakumykov.kotlin_playground.shortcuts_parser.utils.ResourceResolver
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory


class MainActivity : AppCompatActivity() {

    private var shortcuts: Result<List<Shortcut>>? = null
    private val dynamicShortcutManager by lazy { DynamicShortcutManager(this) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.messages.observe(this) { messages -> binding.logView.text = messages.joinToString("\n") }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appInfoButton.setOnClickListener { showAppProperties() }
        binding.clearLogButton.setOnClickListener { Logger.clear() }

        binding.button1.setOnClickListener { action1() }
        binding.button2.setOnClickListener { action2() }
        binding.button3.setOnClickListener { action3() }
        binding.button4.setOnClickListener { action4() }
    }


    private fun action1() {
        val factory = SAXParserFactory.newInstance()
        val saxParser = factory.newSAXParser()
        val baeldungHandler = BaeldungHandler()

        val xmlFileInputStream: InputStream = resources.openRawResource(R.raw.baeldung)
        saxParser.parse(xmlFileInputStream, baeldungHandler)

        val website = baeldungHandler.website
        Log.d(TAG, website.toString())
    }

    private fun action2() {
        val shortcutsParser = ShortcutsParser(
            ShortcutsXMLRawParser(SAXParserFactory.newInstance().newSAXParser(), ShortcutsSAXHandler()),
            RawShortcutResolver(ResourceResolver(packageName, resources))
        )

        shortcuts = shortcutsParser.parse(this, R.raw.shortcuts)

        shortcuts?.getOrNull()?.also { list ->
            list.forEachIndexed { index, shortcut ->
                Logger.d(TAG, "$index) ${shortcut.shortcutId}")
            }
        } ?: {
            showToast("Список ярлыков пуст")
        }

        shortcuts?.getOrNull()?.also {
            dynamicShortcutManager.recreateShortcuts(it)
        } ?: {
            showToast("Нет ярлыков")
        }
    }

    private fun action3() {
        shortcuts?.also {
            it.getOrNull()?.also { list ->
                dynamicShortcutManager.createShortcutsFromList(list)
                showToast("Ярлыки обновлены (пересозданы?)")
            } ?: {
                showToast("Нет инфы о ярлыках")
            }
        } ?: {
            showToast("Прочитайте файл ярлыков")
        }
    }

    private fun action4() {
        shortcuts?.also {
            dynamicShortcutManager.removeAllShortcuts()
            showToast("Ярлыки должны быть удалены")
        } ?: {
            showToast("Прочитайте файл ярлыков")
        }
    }



    private fun log(text:String) = Logger.d(TAG, text)


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}