package com.github.difflib.visitor;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.InsertDelta;

public interface DeltaVisitor<T>
{
	static <T> void accept(AbstractDelta<T> delta, DeltaVisitor<T> visitor)
	{
		if (delta instanceof InsertDelta)
		{
			visitor.visit((InsertDelta<T>) delta);
		}
		else if (delta instanceof ChangeDelta)
		{
			visitor.visit((ChangeDelta<T>) delta);
		}
		else if (delta instanceof DeleteDelta)
		{
			visitor.visit((DeleteDelta<T>) delta);
		}
	}

	void visit(InsertDelta<T> insertDelta);

	void visit(ChangeDelta<T> changeDelta);

	void visit(DeleteDelta<T> deleteDelta);
}
