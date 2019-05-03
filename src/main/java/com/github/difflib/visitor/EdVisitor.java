package com.github.difflib.visitor;

import com.github.difflib.patch.*;

import java.io.PrintWriter;
import java.util.List;

public class EdVisitor implements DeltaVisitor<String>
{
	private final PrintWriter writer;

	public EdVisitor(PrintWriter writer)
	{
		this.writer = writer;
	}

	public void visit(Patch<String> patch)
	{
		final List<AbstractDelta<String>> deltas = patch.getDeltas();
		for (int i = deltas.size() - 1; i >= 0; i--)
		{
			final AbstractDelta<String> delta = deltas.get(i);
			DeltaVisitor.accept(delta, this);
		}
	}

	@Override
	public void visit(InsertDelta<String> insertDelta)
	{
		this.writer.print(insertDelta.getSource().getPosition());
		this.writer.print('a');
		this.writer.print('\n');
		this.printTarget(insertDelta);
	}

	@Override
	public void visit(ChangeDelta<String> changeDelta)
	{
		this.writer.print(changeDelta.getSource().getPosition() + 1);
		this.writer.print('c');
		this.writer.print('\n');
		this.printTarget(changeDelta);
	}

	@Override
	public void visit(DeleteDelta<String> deleteDelta)
	{
		final int sourceStart = deleteDelta.getSource().getPosition() + 1;
		final int sourceLen = deleteDelta.getSource().getLines().size();

		this.writer.print(sourceStart);
		if (sourceLen != 1)
		{
			this.writer.print(',');
			this.writer.print(sourceStart + sourceLen - 1);
		}
		this.writer.print('d');
		this.writer.print('\n');
	}

	// --------------- Helper Methods ---------------

	private void printTarget(AbstractDelta<String> delta)
	{
		for (String line : delta.getTarget().getLines())
		{
			// TODO escape if line=="." https://stackoverflow.com/questions/19608245/how-to-escape-period-in-ed
			this.writer.print(line);
			this.writer.print('\n');
		}
		this.writer.print(".\n");
	}
}
